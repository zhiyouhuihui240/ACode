package cn.hx.plugin.junkcode.utils

import cn.hx.plugin.junkcode.ext.JunkCodeConfig
import cn.hx.plugin.junkcode.template.ResTemplate
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec
import org.gradle.api.Project

import javax.lang.model.element.Modifier
import java.nio.file.Files
import java.nio.file.Path

class JunkUtil {

    static random = new Random()

    static abc = "abcdefghijklmnopqrstuvwxyz".toCharArray()
    static color = "0123456789abcdef".toCharArray()
    static num = "0123456789".toCharArray()
    static List<String> stringList = new ArrayList<>()
    static List<String> stringNameList = new ArrayList<>()
    // 存储其他类的一些数据
    static List<String> otherClassNameList = new ArrayList<>()
    static List<String> otherPackageNameList = new ArrayList<>()
    static List<String> otherClassMethodsNameList = new ArrayList<>()
    static List<String> otherClassMethodsAccessList = new ArrayList<String>()
    static Map<String, List<String>> otherClassMethodsAccessMap = new HashMap<String, List<String>>();


    // 随机生成一个名称
    static String generateName(int index) {
        def sb = new StringBuilder()
        for (i in 0..4) {
            sb.append(abc[random.nextInt(abc.size())])
        }
        int temp = index
        while (temp >= 0) {
            sb.append(abc[temp % abc.size()])
            temp = temp / abc.size()
            if (temp == 0) {
                temp = -1
            }
        }
        sb.append(index.toString())
        return sb.toString()
    }

    static strList = ['GameActivity', 'PreActivity', 'LauncherActivity', 'HomeActivity', 'SplashActivity',
                   'LoadActivity', 'DialogActivity', 'PrivacyActivity', 'BaseActivity', 'HistoryActivity','CameraActivity',
                      'UserActivity', 'MenuActivity', 'VideoActivity', 'GuideActivity', 'AlarmActivity','RecordActivity',
                      'ChallengeActivity', 'DownloadActivity', 'ShareActivity', 'FavoriteActivity', 'ProActivity','PlayActivity',
                      'PayActivity', 'SaveActivity', 'ExitActivity', 'VipActivity', 'MusicActivity','ResultActivity','TimeActivity',
                      'LoadingActivity', 'ReviewActivity', 'CreatorActivity', 'CountActivity', 'DataActivity','ReloadActivity','CertifyActivity',
                      'SelectActivity', 'LoginActivity', 'FindActivity', 'PhotoActivity', 'MemberActivity','EditorActivity','CustomActivity']

    // 生成activity名称 从strList中随机获取一个元素，并将该元素从列表中删除
    static String getRandomActivityName(int index) {
        if (strList.size() == 0) {
            // 若是不够，则自动生成随机字符串
            def name = generateName(index)
            return name
        }
        int randomIndex = Math.floor(Math.random() * strList.size())
        String randomStr = strList[randomIndex]
        strList.remove(randomIndex)
        return randomStr
    }


    // 随机生成一个方法名称
    static String generateRandomMethodsName(int index) {
        def strList = ['getCurrentTime', 'isLoading', 'initData', 'initView', 'pear', 'createFragment', 'getActivityCount',
                       'setPro', 'setPreview', 'startAct', 'setVip', 'getVip', 'getProvy', 'setNumber', 'getNumber', 'setCertify', 'getCertify',
                       'getNative', 'createTimer', 'getServers', 'internalDialog', 'gotoMarket', 'saveString', 'startMainActivity', 'onBackAct',
                       'loadCertificate', 'aniNavHost', 'decryptBase64', 'decryptFile', 'aesEncrypt', 'aesDecrypt', 'crypt', 'xorDecode', 'xorEncodeData',
                       'loadProfile', 'toAboutActivity', 'toPager', 'hasLoaded', 'isOpen', 'showSystemUI', 'hideSystemUI', 'uncompress', 'internalRating']
// 从列表中随机获取一个字符串
//        def randomStr = strList[Math.floor(Math.random() * strList.size())]
        if (strList.size() == 0) {
            // 若是不够，则自动生成随机字符串
            def name = generateName(index)
            return name
        }
        int randomIndex = (int)Math.floor(Math.random() * strList.size())
        String randomStr = strList[randomIndex]
        strList.remove(randomIndex)
        return randomStr
    }


    // 生成随机方法
    static void generateMethods(MethodSpec.Builder methodBuilder) {
        def str = "logg"
        def fullName = "cn.hx.plugin.junkcode.utils.Utils"
        List  values = new ArrayList<>()
        def sdk = ""
        def sdkStr = "logg"
        if (otherPackageNameList.size() > 1 && otherClassNameList.size() > 1 ) {
            // todo: ClassName.get() 可以导入尚未存在的类
            fullName = ClassName.get("${otherPackageNameList.get(1)}", "${otherClassNameList.get(1)}")
            sdk = fetchSDK()
            if (otherClassMethodsAccessMap.get(otherClassNameList.get(1))!= null && otherClassMethodsAccessMap.get(otherClassNameList.get(1)).size() >0) {
//            if (otherClassMethodsAccessMap.get(otherClassNameList.first())!= null && otherClassMethodsAccessMap.get(otherClassNameList.first()).size() >0) {
                values = otherClassMethodsAccessMap.get(otherClassNameList.get(1))
                if (values != null && !values.isEmpty() && values.size() > 0) {
                    String firstValue = values.get(0)
//                    values.remove(0)
                    str = firstValue
                    if (sdk == ClassName.get(Utils.class)) {
                        sdkStr = "logg"
                    }else {
                        sdkStr = firstValue
                    }
                }
            }

        }else {
            fullName = ClassName.get(Utils.class)
        }

        if (str == "logg") {
            fullName = ClassName.get(Utils.class)
        }
        if (fullName == ClassName.get(Utils.class)) {
            str = "logg"
        }


        sdk = ClassName.get("com.bytedance.sdk.component.utils")

        switch (random.nextInt(5)) {
            case 0:
                otherClassMethodsAccessList.add("void")
                methodBuilder.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .addStatement("long now = \$T.currentTimeMillis()", System.class)
                        .beginControlFlow("if (\$T.currentTimeMillis() < now)", System.class)
                        .addStatement("\$T.out.println(\$S)", System.class, "Time travelling, woo hoo!")
                        .nextControlFlow("else if (\$T.currentTimeMillis() == now)", System.class)
                        .addStatement("\$T.out.println(\$S)", System.class, "Time stood still!")
                        .nextControlFlow("else")
                        .addStatement("\$T.out.println(\$S)", System.class, "Ok, time still moving forward")
                        .addStatement("\$T.$str()", fullName)
                        .addStatement("\$T.$sdkStr()", sdk)
                        .endControlFlow()
                break
            case 1:
                otherClassMethodsAccessList.add("void")
                methodBuilder.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .addStatement("\$T.$str()", fullName)
                        .addCode("" + "int total = 0;\n" + "for (int i = 0; i < 10; i++) {\n" + "  total += i;\n" + "}\n")
                break
            case 2:
                otherClassMethodsAccessList.add("void")
                methodBuilder.addModifiers(Modifier.PUBLIC, Modifier.STATIC).beginControlFlow("try")
                        .addStatement("throw new Exception(\$S)", "Failed")
                        .nextControlFlow("catch (\$T e)", Exception.class)
                        .addStatement("\$T.$str()", fullName)
                        .endControlFlow()
                break
            case 3:
                methodBuilder.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .addStatement("\$T.$str()", fullName)
                        .returns(Date.class)
                        .addStatement("return new \$T()", Date.class)
                break
            case 4:
                otherClassMethodsAccessList.add("public static void")
                methodBuilder.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .addStatement("\$T.$str()", fullName)
                        .returns(void.class)
                        .addParameter(String[].class, "args")
                        .addStatement("\$T.out.println(\$S)", System.class, "Hello")
                break
            case 5:
                otherClassMethodsAccessList.add("public static void")
                methodBuilder.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .addStatement("\$T.$str()", fullName)
                break
            //todo：添加随机方法
            default:
                otherClassMethodsAccessList.add("public static void")
                methodBuilder.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .addStatement("\$T.$str()", fullName)
                        .returns(void.class)
                        .addParameter(String[].class, "args")
                        .addStatement("\$T.out.println(\$S)", System.class, "Hello")
        }
    }


    // todo: 判断是否是 SDK 路径下生成的文件
    static ClassName fetchSDK(){
        def sdk = "cn.hx.plugin.junkcode.utils.Utils"
        if (otherPackageNameList.size() > 1 && otherClassNameList.size() > 1 ) {
            sdk = otherPackageNameList.get(1)
            switch (sdk) {
                case "com.bytedance.sdk.component.utils":
                    sdk = ClassName.get("${otherPackageNameList.get(1)}", "${otherClassNameList.get(1)}")
                    break
                case "com.google.android.gms.ads.identifier":
                    sdk = ClassName.get("${otherPackageNameList.get(1)}", "${otherClassNameList.get(1)}")
                    break
                case "com.iab.omid.library.applovin":
                    sdk = ClassName.get("${otherPackageNameList.get(1)}", "${otherClassNameList.get(1)}")
                    break
                case "com.anythink.core.activity":
                    sdk = ClassName.get("${otherPackageNameList.get(1)}", "${otherClassNameList.get(1)}")
                    break
                case "com.bytedance":
                    sdk = ClassName.get("${otherPackageNameList.get(1)}", "${otherClassNameList.get(1)}")
                    break
                default:
                    sdk = ClassName.get("${otherPackageNameList.get(1)}", "${otherClassNameList.get(1)}")
            }

        }
        return sdk
    }



    // 生成 Activity 随机方法
    static void generateActivityMethods(MethodSpec.Builder methodBuilder) {
        switch (random.nextInt(5)) {
            case 0:
                methodBuilder.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .addStatement("long now = \$T.currentTimeMillis()", System.class)
                        .beginControlFlow("if (\$T.currentTimeMillis() < now)", System.class)
                        .addStatement("\$T.out.println(\$S)", System.class, "Time travelling, woo hoo!")
                        .nextControlFlow("else if (\$T.currentTimeMillis() == now)", System.class)
                        .addStatement("\$T.out.println(\$S)", System.class, "Time stood still!")
                        .nextControlFlow("else")
                        .addStatement("\$T.out.println(\$S)", System.class, "Ok, time still moving forward")
                        .endControlFlow()
                break
            case 1:
                methodBuilder.addModifiers(Modifier.PUBLIC, Modifier.STATIC).addCode("" + "int total = 0;\n" + "for (int i = 0; i < 10; i++) {\n" + "  total += i;\n" + "}\n")
                break
            case 2:
                methodBuilder.addModifiers(Modifier.PUBLIC, Modifier.STATIC).beginControlFlow("try")
                        .addStatement("throw new Exception(\$S)", "Failed")
                        .nextControlFlow("catch (\$T e)", Exception.class)
                        .endControlFlow()
                break
            case 3:
                methodBuilder.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .returns(Date.class)
                        .addStatement("return new \$T()", Date.class)
                break
            case 4:
                methodBuilder.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .returns(void.class)
                        .addParameter(String[].class, "args")
                        .addStatement("\$T.out.println(\$S)", System.class, "Hello")
                break
            case 5:
                methodBuilder.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                break
                //todo：添加随机方法
            default:
                methodBuilder.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .returns(void.class)
                        .addParameter(String[].class, "args")
                        .addStatement("\$T.out.println(\$S)", System.class, "Hello")
        }
    }



    // 生成颜色代码
    static String generateColor() {
        def sb = new StringBuilder()
        sb.append("#")
        for (i in 0..5) {
            sb.append(color[random.nextInt(color.size())])
        }
        return sb.toString()
    }


    // 生成id代码
    static String generateId() {
        def sb = new StringBuilder()
        for (i in 0..5) {
            sb.append(abc[random.nextInt(abc.size())])
        }
        return sb.toString()
    }


    static List integrityName = new ArrayList<String>()
    // 生成activity
    static List<String> generateActivity(File javaDir, File resDir, String namespace, String packageName, JunkCodeConfig config) {
        otherPackageNameList.add(0, packageName)
        def activityList = new ArrayList()
        for (int i = 0; i < config.activityCountPerPackage; i++) {
            def className
            def layoutName
            if (config.activityCreator) {
                def activityNameBuilder = new StringBuilder()
//                def activityNameBuilder = getRandomActivityName(i)
                def layoutNameBuilder = new StringBuilder()
                def layoutContentBuilder = new StringBuilder()
//                config.activityCreator.execute(new Tuple4(i, getRandomActivityName(i), layoutNameBuilder, layoutContentBuilder))
                config.activityCreator.execute(new Tuple4(i, activityNameBuilder, layoutNameBuilder, layoutContentBuilder))
                className = activityNameBuilder.toString()
                // todo: 保存activity的类名
                otherClassNameList.add(0, className)
                layoutName = layoutNameBuilder.toString()
                writeStringToFile(new File(resDir, "layout/${layoutName}.xml"), layoutContentBuilder.toString())
            } else {
//                def activityPreName = generateName(i)
                // todo：从固有列表中随机获取任意一个 Activity 名称
                def activityPreName = getRandomActivityName(i)
                // activityPreName.capitalize() 将首字母大写  再拼接 Activity
                className = activityPreName.capitalize() + "Activity"
                // todo: 保存activity的类名
                otherClassNameList.add(0, className)
                layoutName = "${config.resPrefix.toLowerCase()}${packageName.replace(".", "_")}_activity_${activityPreName}"
                generateLayout(resDir, layoutName, config)
            }
            if (!config.excludeActivityJavaFile) {
                def typeBuilder = TypeSpec.classBuilder(className)
                // todo:   activity的继承父类更改为AppCompatActivity
//                typeBuilder.superclass(ClassName.get("android.app", "Activity"))
                typeBuilder.superclass(ClassName.get("androidx.appcompat.app", "AppCompatActivity"))
                typeBuilder.addModifiers(Modifier.PUBLIC)
                if (config.typeGenerator) {
                    config.typeGenerator.execute(typeBuilder)
                } else {
                    // 下一个方法，对之前的数据进行清理
                    stringNameList.clear()
                    stringList.clear()
                    //其它方法
                    for (int j = 0; j < config.methodCountPerClass; j++) {
                        def methodName
                        if (config.methodNameCreator) {
                            def methodNameBuilder = new StringBuilder()
                            config.methodNameCreator.execute(new Tuple2(j, methodNameBuilder))
                            methodName = methodNameBuilder.toString()
                        } else {
//                            methodName = generateRandomMethodsName(j)
                            methodName = generateName(j)
                        }

                        stringNameList.add(methodName)
                        def methodBuilder = MethodSpec.methodBuilder(methodName)
                        if (config.methodGenerator) {
                            config.methodGenerator.execute(methodBuilder)
                        } else {
                            generateActivityMethods(methodBuilder)
                        }
                        typeBuilder.addMethod(methodBuilder.build())
                        // 只添加没有参数的方法，且将新添加的数据放在首位
                        if (methodBuilder.build().parameters.size() == 0) {
                            stringList.add(methodBuilder.build().name)
                            otherClassMethodsNameList.add(0,methodBuilder.build().name)
//                            otherClassMethodsAccessMap.put(className, methodBuilder.build().name)


                            if (otherClassMethodsAccessMap.containsKey(className)) {
                                otherClassMethodsAccessMap.get(className).add(methodBuilder.build().name);
                            } else {
                                List<String> values = new ArrayList<String>();
                                values.add(methodBuilder.build().name);
                                otherClassMethodsAccessMap.put(className, values);
                            }


                        }
                    }
                }

                //onCreate方法
                def bundleClassName = ClassName.get("android.os", "Bundle")

                typeBuilder.addMethod(MethodSpec.methodBuilder("onCreate")
                        .addAnnotation(Override.class)
                        .addModifiers(Modifier.PROTECTED)
                        .addParameter(bundleClassName, "savedInstanceState")
                        .addStatement("super.onCreate(savedInstanceState)")
                        .addStatement("setContentView(\$T.layout.${layoutName})", ClassName.get(namespace, "R"))
                        // todo: 添加调用方法
                        .addStatement(getRandomMethod())
                        .addStatement(getRandomMethod())
                        .addStatement(getRandomMethod())
                        .build())

                typeBuilder.addMethod(MethodSpec.methodBuilder("onResume")
                        .addAnnotation(Override.class)
                        .addModifiers(Modifier.PROTECTED)
                        .addStatement("super.onResume()")
                        .addStatement(getRandomMethod())
                        .addStatement(getRandomMethod())
                        .build())

                typeBuilder.addMethod(MethodSpec.methodBuilder("onDestroy")
                        .addAnnotation(Override.class)
                        .addModifiers(Modifier.PROTECTED)
                        .addStatement("super.onDestroy()")
                        .build())

                typeBuilder.addMethod(MethodSpec.methodBuilder("onBackPressed")
                        .addAnnotation(Override.class)
                        .addModifiers(Modifier.PUBLIC)
                        .addStatement("super.onBackPressed()")
                        .build())

                def javaFile = JavaFile.builder(packageName, typeBuilder.build()).build()
                writeJavaToFile(javaDir, javaFile)
                // todo：保存activity的包名
                activityList.add(packageName + "." + className)
                integrityName.add(packageName + "." + className)
            }
        }
        return activityList
    }


    static Integer generateRandomNum() {
        def sb = new StringBuilder()
        sb.append(num[random.nextInt(num.size())])
        return sb.toInteger()
    }

    static String getRandomMethod(){
        def unm = generateRandomNum()
        def num = stringList.size()
        if (unm >= num) {
            unm = 0
        }
        return "${stringList[unm]}()"
    }


    // 生成其他类文件
    static void generateJava(File javaDir, String packageName, JunkCodeConfig config) {
        otherPackageNameList.add(0, packageName)
        for (int i = 0; i < config.otherCountPerPackage; i++) {
            def className
            if (config.classNameCreator) {
                def classNameBuilder = new StringBuilder()
                config.classNameCreator.execute(new Tuple2(i, classNameBuilder))
                className = classNameBuilder.toString()
            } else {
                className = generateName(i).capitalize()
            }
            def typeBuilder = TypeSpec.classBuilder(className)
            otherClassNameList.add(0, className)
            if (config.typeGenerator) {
                config.typeGenerator.execute(typeBuilder)
            } else {
                typeBuilder.addModifiers(Modifier.PUBLIC)
//                otherClassMethodsNameList.clear()
                for (int j = 0; j < config.methodCountPerClass; j++) {
                    def methodName
                    if (config.methodNameCreator) {
                        def methodNameBuilder = new StringBuilder()
                        config.methodNameCreator.execute(new Tuple2(j, methodNameBuilder))
                        methodName = methodNameBuilder.toString()
                    } else {
//                        methodName = generateRandomMethodsName(j)
                        methodName = generateName(j)
                    }
                    def methodBuilder = MethodSpec.methodBuilder(methodName)
                    if (config.methodGenerator) {
                        config.methodGenerator.execute(methodBuilder)
                    } else {
                        generateMethods(methodBuilder)
                    }
                    typeBuilder.addMethod(methodBuilder.build())

                    // 只添加没有参数的方法，且将新添加的数据放在首位
                    if (methodBuilder.build().parameters.size() == 0) {
                        otherClassMethodsNameList.add(0,methodBuilder.build().name)
//                        otherClassMethodsAccessMap.put(className, methodBuilder.build().name)

                        if (otherClassMethodsAccessMap.containsKey(className)) {
                            otherClassMethodsAccessMap.get(className).add(methodBuilder.build().name);
                        } else {
                            List<String> values = new ArrayList<String>();
                            values.add(methodBuilder.build().name);
                            otherClassMethodsAccessMap.put(className, values);
                        }
                    }
//                    otherClassMethodsNameList.removeLast()
                }
            }
            def javaFile = JavaFile.builder(packageName, typeBuilder.build()).build()
            writeJavaToFile(javaDir, javaFile)
        }
    }


    // 生成 layout 文件
    static void generateLayout(File resDir, String layoutName, JunkCodeConfig config) {
        def layoutFile = new File(resDir, "layout/${layoutName}.xml")
        if (config.layoutGenerator) {
            def contentBuilder = new StringBuilder()
            config.layoutGenerator.execute(contentBuilder)
            writeStringToFile(layoutFile, contentBuilder.toString())
        } else {
            def layoutStr = String.format(ResTemplate.LAYOUT_TEMPLATE, generateId())
            writeStringToFile(layoutFile, layoutStr)
        }
    }


    // 生成 drawable
    static void generateDrawableFiles(File resDir, JunkCodeConfig config) {
        if (config.drawableGenerator) {
            def contentBuilder = new StringBuilder()
            for (int i = 0; i < config.drawableCount; i++) {
                def drawableName = "${config.resPrefix.toLowerCase()}${generateName(i)}"
                contentBuilder.setLength(0)
                config.drawableGenerator.execute(contentBuilder)
                writeStringToFile(new File(resDir, "drawable/${drawableName}.xml"), contentBuilder.toString())
            }
        } else if (config.drawableCreator) {
            def fileNameBuilder = new StringBuilder()
            def contentBuilder = new StringBuilder()
            for (int i = 0; i < config.drawableCount; i++) {
                fileNameBuilder.setLength(0)
                contentBuilder.setLength(0)
                config.drawableCreator.execute(new Tuple3(i, fileNameBuilder, contentBuilder))
                def drawableName = fileNameBuilder.toString()
                writeStringToFile(new File(resDir, "drawable/${drawableName}.xml"), contentBuilder.toString())
            }
        } else {
            for (int i = 0; i < config.drawableCount; i++) {
                def drawableName = "${config.resPrefix.toLowerCase()}${generateName(i)}"
                writeStringToFile(new File(resDir, "drawable/${drawableName}.xml"), String.format(ResTemplate.DRAWABLE, generateColor()))
            }
        }
    }


    // 生成 strings.xml
    static void generateStringsFile(File resDir, JunkCodeConfig config) {
        def stringFile = new File(resDir, "values/strings.xml")
        StringBuilder contentBuilder = new StringBuilder()
        StringBuilder keyBuilder = new StringBuilder()
        StringBuilder valueBuilder = new StringBuilder()
        contentBuilder.append("<resources>\n")
        for (int i = 0; i < config.stringCount; i++) {
            def key
            def value
            if (config.stringCreator) {
                keyBuilder.setLength(0)
                valueBuilder.setLength(0)
                config.stringCreator.execute(new Tuple3(i, keyBuilder, valueBuilder))
                key = keyBuilder.toString()
                value = valueBuilder.toString()
            } else {
                key = "${config.resPrefix.toLowerCase()}${generateName(i)}"
                value = generateName(i)
            }
            contentBuilder.append("<string name=\"${key}\">${value}</string>\n")
        }
        contentBuilder.append("</resources>\n")
        writeStringToFile(stringFile, contentBuilder.toString())
    }

    /**
     * 生成keep.xml
     * @param resDir
     * @param config
     */
    static void generateKeep(File resDir, JunkCodeConfig config) {
        def keepName
        def keepContent
        if (config.keepCreator) {
            def fileNameBuilder = new StringBuilder()
            def contentBuilder = new StringBuilder()
            config.keepCreator.execute(new Tuple2(fileNameBuilder, contentBuilder))
            keepName = fileNameBuilder.toString()
            keepContent = contentBuilder.toString()
        } else {
            if (config.resPrefix.isEmpty()) {
                return
            }
            keepName = "android_junk_code_keep"
            keepContent = "<resources xmlns:tools=\"http://schemas.android.com/tools\"\n" +
                    "    tools:keep=\"@layout/${config.resPrefix}*, @drawable/${config.resPrefix}*\" />\n"
        }
        def keepFile = new File(resDir, "raw/${keepName}.xml")
        writeStringToFile(keepFile, keepContent)
    }



    // 生成AndroidManifest.xml, todo: 顺序打乱, 读取 manifest文件,然后以数组的形式来将顺序打乱
    static void generateManifest(File manifestFile, List<String> activityList) {
        StringBuilder sb = new StringBuilder()
        sb.append("<manifest xmlns:android=\"http://schemas.android.com/apk/res/android\">\n")
//        sb.append("<uses-permission android:name=\"android.permission.INTERNET\" />\n")
        sb.append("    <application>\n")
        for (i in 0..<activityList.size()) {
            sb.append("        <activity android:name=\"${activityList.get(i)}\"  android:exported=\"false\"/>\n")
                sb.append("        <service android:name=\"${CoonUtil.generateServiceName(i)}\"  android:exported=\"false\"/>\n")
            sb.append("        <meta-data android:name=\"${CoonUtil.generateMetaDataName(i)}\"   android:value=\"${CoonUtil.generateRandomabcABC123(i)}\"/>\n")
        }
        sb.append("    </application>\n")
        sb.append("</manifest>")
        writeStringToFile(manifestFile, sb.toString())
    }


    /**
     * 生成proguard-rules.pro
     * @param manifestFile
     * @param activityList
     */
    static void generateProguard(File proguardFile, List<String> packageList) {
        StringBuilder sb = new StringBuilder()
        for (i in 0..<packageList.size()) {
            sb.append("-keep class ${packageList.get(i)}.**{*;}\n")
        }
        writeStringToFile(proguardFile, sb.toString())
    }

    /**
     * java写入文件
     * @param javaDir
     * @param javaFile
     */
    static void writeJavaToFile(File javaDir, JavaFile javaFile) {
        def outputDirectory = javaDir.toPath()
        if (!javaFile.packageName.isEmpty()) {
            for (String packageComponent : javaFile.packageName.split("\\.")) {
                outputDirectory = outputDirectory.resolve(packageComponent);
            }
            Files.createDirectories(outputDirectory);
        }
        Path outputPath = outputDirectory.resolve(javaFile.typeSpec.name + ".java");
        writeStringToFile(outputPath.toFile(), javaFile.toString())
    }

    /**
     * 字符串写入文件
     * @param file
     * @param data
     */
    static void writeStringToFile(File file, String data) {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs()
        }
        FileWriter writer
        try {
            writer = new FileWriter(file)
            writer.write(data)
        } catch (Exception e) {
            e.printStackTrace()
        } finally {
            if (writer != null) {
                writer.close()
            }
        }
    }

    /**
     * 是否是AGP7.0.0及以上
     * @param project
     * @return
     */
    static boolean isAGP7_0_0(Project project) {
        def androidComponents = project.extensions.findByName("androidComponents")
        if (androidComponents && androidComponents.hasProperty("pluginVersion")) {
            return true
        }
        return false
    }
    /**
     * 是否是AGP7.4.0及以上
     * @param project
     * @return
     */
    static boolean isAGP7_4_0(Project project) {
        def androidComponents = project.extensions.findByName("androidComponents")
        if (androidComponents && androidComponents.hasProperty("pluginVersion") && (androidComponents.pluginVersion.major > 7 || androidComponents.pluginVersion.minor >= 4)) {
            return true
        }
        return false
    }
}