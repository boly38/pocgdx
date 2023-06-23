package com.olity.pocgdx.ui.tools;

@FunctionalInterface
public interface SimpleCallable {
    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     */
    void call();
}
