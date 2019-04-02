
# TripleA 

> **TripleA** is a framework that aims to reduce boilerplate code when writing Android Application.

[![Version](https://img.shields.io/badge/Version-0.1.α-blue.svg)](https://github.com/xgouchet/TripleA)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Kotlin 1.2.10](https://img.shields.io/badge/Kotlin-1.2.50-blue.svg)](http://kotlinlang.org)


In modern Android development, clean architecture principles entice us to split our logic into lots of classes : front (MVP, MVVM, MVMPVVPVM™, …), 
back (Room, SQLight, Realm, …), functional communication (RXJava, Agera, Kotlin Coroutines, …). 
**TripleA** is able to generate a whole bunch of boilerplate code that you'd otherwise  have to write by hand for every single screen in your application. 

## Installation

### Gradle

Add the following to add the TripleA plugin to your project's `build.gradle`.

```groovy
buildscript {
    repositories {
        maven { url "https://jitpack.io" }
    }
    dependencies {
        classpath  "com.github.xgouchet.TripleA:triplea-plugin:0.1.0"
    }
}
```

Then add the following to your app's `build.gradle`.

```groovy
    apply plugin: 'tripleA'
    
    dependencies {
        implementation "com.github.xgouchet.TripleA:triplea-core:0.1.0"
    }
```


## Usage 

By default, the plugin will look for a `definition.json` file at the root of your app's module (so right next to your app's `build.gradle` file). 
This Json file will describe the features that the plugin will generate for you. 

### Global Definition 

The basic `definition.json`'s structure should look like this : 

```json
{
    "package_name": "com.sample.foo.generated",
    "application_id": "com.sample.foo.androidapp",
    "features": []
}
```

 - `package_name` : the package in which all generated classes will be;
 - `application_id` : your android application id as it is declared in your `build.gradle` file; 
 - `features` : an array which can hold as many feature as you need.

### Feature definition

A feature's structure should look like this : 

```json
{
    "key": "users",
    "template": "list",
    "layers": ["front-mvp", "back-realm"],
    "base_type": {
        "simple_name": "User",
        "canonical_name": "com.sample.foo.models.User"
    },
    "params": {}
}
```

 - `key` : a **unique** identifier for the feature, which will also be used as a sub-package name for the generated classes;
 - `template` : the template name (cf the list of available templates below);
 - `layers` : an array of layers to generate for the feature (cf the list of available layers below);
 - `base_type` : the type of data that is key to the feature;
 - `params` : a dictionary of Strings that holds parameters for the various layers (each layer have their own).
 
## Reference
 
### Layers 

 - `front-mvp` : this layer generates a Frontend layer using the MVP architecture
 - `front-mvvm` : this layer generates a Frontend layer using the MVVM architecture 


## Release History

 * This library is currently in alpha version, use at your own risks

## Contribute

If you want to contribute, please follow the [Contribution guidelines](CONTRIBUTING.md).

## See Also

 - Contact me on Twitter [@xgouchet](https://twitter.com/xgouchet)

## License

This program is distributed under the [Apache License 2.0](https://opensource.org/licenses/Apache-2.0)
