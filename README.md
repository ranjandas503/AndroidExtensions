# AndroidExtensions

<b>This library is basically a collection of helper methods, which we use while building android apps.</b>


Just add following dependency and you'r build gradle and your are good to go
```
implementation 'com.menasr.andyext:andyextensions:1.0.1'
```
Or
```
<dependency>
  <groupId>com.menasr.andy</groupId>
  <artifactId>andy</artifactId>
  <version>1.0.1</version>
  <type>pom</type>
</dependency>
```

All the methods are self explainatory, there is also a sample in it. For better understanding of the project. Some functions like,

```
toast(R.string.<yourStringId>)
```
Or
```
toast("My custom message")
```

Similarly, other functions are like, 
logr,loge,logv,logd and many more. While going to production, if you don't want to print the logs at all, then call
```
Andy.disposeAlllogs(true)
```

There are tons of other functions and features, like lazy loading adapter, recycler view intialization apapter and much more. All the methods are self explainatory.
