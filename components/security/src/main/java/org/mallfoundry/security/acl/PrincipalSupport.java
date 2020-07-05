package org.mallfoundry.security.acl;

public abstract class PrincipalSupport implements MutablePrincipal {

    @Override
    public Builder toBuilder() {
        return new BuilderSupport(this) {
        };
    }

    protected abstract static class BuilderSupport implements Builder {
        private final MutablePrincipal principal;

        public BuilderSupport(MutablePrincipal principal) {
            this.principal = principal;
        }

        @Override
        public Builder name(String name) {
            this.principal.setName(name);
            return this;
        }

        @Override
        public Builder type(String type) {
            this.principal.setType(type);
            return this;
        }

        @Override
        public Principal build() {
            return this.principal;
        }
    }
}
