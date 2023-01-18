package com.github.hakkazuu.prettyload;

abstract class PrettyItem {

    abstract void start();

    abstract void stop();

    abstract String getTag();

    abstract int getId();

}