```
   Kotlin项目中集成Arouter 
```

#### 一、功能介绍
1. **kotlin项目中引入方式**
   ``` gradle
    ...
    apply plugin: 'kotlin-kapt'
    android {
        defaultConfig {
            ...
            kapt {
                arguments {
                    arg("AROUTER_MODULE_NAME", project.getName())
                    arg("AROUTER_GENERATE_DOC", "enable")
                }
            }
        }
    }

    dependencies {
        //  implementation 'com.alibaba:fastjson:1.2.32'  保证没错
        // 替换成最新版本, 需要注意的是api
        // 要与compiler匹配使用，均使用最新版可以保证兼容
        implementation 'com.alibaba:arouter-api:1.5.0'
        kapt 'com.alibaba:arouter-compiler:1.2.2'
        //依赖其它module
        ...
    }
    ```
2. **使用方法**
    1. 从外部URL映射到内部页面，以及参数传递与解析
    2. 跨模块页面跳转，模块间解耦
    3. 拦截跳转过程，处理登陆、埋点等逻辑
    4. 跨模块API调用，通过控制反转来做组件解耦
3. **遇到的问题**

    1. ##### 默认支持serializable和parcelable|||如果要支持直接传递object则需要创建一个实现类实现  SerializationService
    2. ##### fastJson1.2.48无法parseObject   com.alibaba:fastjson:1.2.32 这个版本没问题
    3. ##### 出现的问题:kotlin的 default constructor not found
             *  解决:
             *       1..修改fastjson版本为: implementation 'com.alibaba:fastjson:1.2.32'  这个还可以解决map无法解析的问题(第二问题)
             *    或 2..添加: implementation "org.jetbrains.kotlin:kotlin-reflect:$kotlin_version"
             *
             *
             *  出现的问题: com.alibaba.fastjson.JSONException: TODO
