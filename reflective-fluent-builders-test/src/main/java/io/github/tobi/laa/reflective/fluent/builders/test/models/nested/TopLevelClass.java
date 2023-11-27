package io.github.tobi.laa.reflective.fluent.builders.test.models.nested;

import lombok.Data;

@Data
public class TopLevelClass {

    private NestedPublicLevelOne nestedPublic;

    private NestedPackagePrivateLevelOne nestedPackagePrivate;

    private NestedProtectedLevelOne nestedProtected;

    private NestedPrivateLevelOne nestedPrivate;

    private NestedNonStatic nestedNonStatic;

    @Data
    public static class NestedPublicLevelOne {

        private NestedPublicLevelTwo nested;

        @Data
        public static class NestedPublicLevelTwo {

            private NestedPublicLevelThree nested;

            @Data
            public static class NestedPublicLevelThree {
                private int field;
            }
        }
    }

    @Data
    static class NestedPackagePrivateLevelOne {
        private int field;
    }

    @Data
    protected static class NestedProtectedLevelOne {
        private int field;
    }

    @Data
    private static class NestedPrivateLevelOne {
        private int field;
    }

    @Data
    @SuppressWarnings("all")
    public class NestedNonStatic {
        private int field;
    }
}
