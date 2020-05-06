package de.comparus.opensource.longmap.entity;

import java.util.Objects;

public class Foo {
    private long l;
    private String s;
    private boolean b;

    public Foo(long l, String s, boolean b) {
        this.l = l;
        this.s = s;
        this.b = b;
    }

    public long getL() {
        return l;
    }

    public String getS() {
        return s;
    }

    public boolean getB() {
        return b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Foo foo = (Foo) o;
        return l == foo.l &&
                b == foo.b &&
                Objects.equals(s, foo.s);
    }

    @Override
    public int hashCode() {
        return Objects.hash(l, s, b);
    }
}
