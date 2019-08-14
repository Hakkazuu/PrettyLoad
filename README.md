# **PrettyLoad**

`PrettyLoad` is a library for animate views while loading data.

![](https://i.imgur.com/FudCG4g.gif)

## **Integration**

Keep in mind that `PrettyLoad` has min API level 16.

In Project `build.gradle`:
```groovy
repositories {
    jcenter()
}
```

In module `build.gradle`:
```groovy
compile 'com.github.hakkazuu:prettyload:0.0.1'
```

## **Sample**
First, use `@MakePretty` annotation for target views:
```java
    @MakePretty private TextView prettyTextView;
    @MakePretty private EditText prettyEditText;
```

You need to initialize `PrettyLoad` in your activity `onCreate`:
```java
    PrettyLoad.init(this, this)...
```
or fragment `onCreateView`:
```java
    PrettyLoad.init(getContext(), this)...
```

Then you can configure it. 
As example:
```java
    ...
    .setDrawable(R.drawable.rounded_background)
    .setDuration(1000)
    .setColors(R.color.green1, R.color.green2, R.color.green3);
```

In example `rounded_background.xml` contains:
```xml
    <?xml version="1.0" encoding="utf-8"?>
    <shape xmlns:android="http://schemas.android.com/apk/res/android">
	<solid android:color="@android:color/white" />
	<corners android:radius="4dp" />
    </shape>
```

Finally, call `PrettyLoad.start()` when something is loading and `PrettyLoad.stop()` after data is ready.

## **License**

    Copyright 2019 Anton Shakhov
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
        http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
