package io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy;

@lombok.Setter
public class ClassWithHierarchy extends FirstSuperClass implements AnInterface {

    private int one;

    public static void main(String[] args) {
        final var f = new ClassWithHierarchy();
        f.setTwo(2);
        f.setSeven(2);
    }
}
