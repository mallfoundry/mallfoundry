package org.mallfoundry.catalog;

import org.mallfoundry.util.Positions;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Transactional
    public TopCategory createTopCategory(String name) {
        TopCategory category = this.categoryRepository.save(new TopCategory(name));
        List<TopCategory> categories = this.getTopCategories();
        Positions.sort(categories);
        return category;
    }

    @Transactional
    public ChildCategory addChildCategory(Integer parentId, ChildCategory category) {
        this.getCategory(parentId).addChildCategory(category);
        return category;
    }

    @SuppressWarnings("unchecked")
    public <S extends Category> S getCategory(Integer id) {
        return (S) this.categoryRepository.findById(id).orElseThrow();
    }

    public List<TopCategory> getTopCategories() {
        return this.categoryRepository
                .findAll(Example.of(new TopCategory()),
                        Sort.by(Sort.Order.asc("position")));
    }

    public List<ChildCategory> getChildCategories(Integer id) {
        return this.categoryRepository.findById(id).orElseThrow().getChildren();
    }

}
