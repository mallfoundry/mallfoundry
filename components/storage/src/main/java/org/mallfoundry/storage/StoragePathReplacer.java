/*
 * Copyright (C) 2019-2020 the original author or authors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.mallfoundry.storage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FilenameUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StoragePathReplacer {

    private static final DateTimeFormatter YYYY_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy");
    private static final DateTimeFormatter MM_DATE_FORMATTER = DateTimeFormatter.ofPattern("MM");
    private static final DateTimeFormatter DD_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd");

    private final String path;

    private final String filename;

    public StoragePathReplacer(String path, String filename) {
        this.path = path;
        this.filename = filename;
    }

    public String replace(BlobResource resource) {
        var replacements = this.createReplacements(resource);
        var path = this.replace(replacements, FilenameUtils.normalize(this.path, true));
        var filename = this.replace(replacements, FilenameUtils.getName(this.filename));
        return FilenameUtils.separatorsToUnix(FilenameUtils.concat(path, filename));
    }

    private String replace(List<Replacement> replacements, String path) {
        var replacePath = path;
        for (var replacement : replacements) {
            replacePath = replacement.replace(replacePath);
        }
        return replacePath;
    }

    private List<Replacement> createReplacements(BlobResource resource) {
        var date = LocalDate.now();
        String filename = FilenameUtils.getBaseName(resource.getPath());
        String extension = FilenameUtils.getExtension(resource.getPath());
        return List.of(
                new Replacement("\\{yyyy\\}", YYYY_DATE_FORMATTER.format(date)),
                new Replacement("\\{MM\\}", MM_DATE_FORMATTER.format(date)),
                new Replacement("\\{dd\\}", DD_DATE_FORMATTER.format(date)),
                new Replacement("\\{name\\}", filename),
                new Replacement("\\{extension\\}", extension)
        );
    }

    @Getter
    @Setter
    @AllArgsConstructor
    static class Replacement {
        private String regex;
        private String replacement;

        public String replace(String path) {
            return path.replaceAll(this.regex, this.replacement);
        }
    }
}
