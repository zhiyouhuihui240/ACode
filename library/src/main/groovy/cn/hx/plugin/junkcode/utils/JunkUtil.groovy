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
    static List<String> otherPackageMethodsNameList = new ArrayList<>()
    static List<String> otherClassMethodsNameList = new ArrayList<>()
    static List<String> otherClassMethodsAccessList = new ArrayList<>()
    static firstNum = -1

    // 随机生成一个activity名称
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
        def strList = ['getCurrentTime', 'isLoading', 'initData', 'initView', 'pear','createFragment','getActivityCount',
                       'setPro', 'setPreview', 'startAct', 'setVip', 'getVip','getPro','setNumber','getNumber','setCertify','getCertify',
                       'getNative', 'createTimer', 'getServers', 'internalRating', 'gotoMarket','saveString','startMainActivity','onBackAct',
                        'loadCertificate', 'aniNavHost', 'decryptBase64', 'decryptFile', 'aesEncrypt','aesDecrypt','crypt','xorDecode','xorEncodeData',
                       'loadProfile', 'toAboutActivity', 'toPager', 'hasLoaded', 'isOpen','showSystemUI','hideSystemUI','uncompress','internalRating']
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

//        otherClassMethodsNameList.add(0,methodBuilder.name)
//        myList["${otherClassMethodsAccessList}"] = ["${methodBuilder.name}"]
        firstNum += 1
        def str = "logg"
        if (otherClassMethodsNameList.size()> 0 ) {
            str = otherClassMethodsNameList[otherClassMethodsNameList.size() -1]
            otherClassMethodsNameList.removeLast()
        }


        def fullName = "cn.hx.plugin.junkcode.utils.Utils"
//        if (otherPackageNameList.size() >5 && otherClassNameList.size() > 5 ) {
//            fullName = "${otherPackageNameList.get(3)}.${otherClassNameList.get(3)}"
//            otherPackageNameList.remove(3)
//            otherClassNameList.remove(3)
//        }

//        Class<?> clazz = Class.forName(fullName);
//
//        Object instance = clazz.getDeclaredConstructor().newInstance()

        switch (random.nextInt(5)) {
            case 0:
                otherClassMethodsAccessList.add("void")
                methodBuilder.addStatement("long now = \$T.currentTimeMillis()", System.class)
                        .beginControlFlow("if (\$T.currentTimeMillis() < now)", System.class)
                        .addStatement("\$T.out.println(\$S)", System.class, "Time travelling, woo hoo!")
                        .nextControlFlow("else if (\$T.currentTimeMillis() == now)", System.class)
                        .addStatement("\$T.out.println(\$S)", System.class, "Time stood still!")
                        .nextControlFlow("else")
                        .addStatement("\$T.out.println(\$S)", System.class, "Ok, time still moving forward")

//                        .addStatement((System.getProperty("FULL_NAME", fullName) != null  ? "${str}(), ${Class.forName(System.getProperty("FULL_NAME", fullName))}" : "${str}()")) // 方法名列表

                        .addStatement("\$T.logg()", Utils.class)

//                        .addStatement("\$T.str()", "${instance.class}")
//                        .addStatement( "${instance}")
//                        .addStatement( "${instance.class}")
//                        .addStatement( "${instance.properties.getClass()}")
//                        .addStatement("\$T.$str()", "${cn.hx.plugin.junkcode.utils.Utils.class}")
//                        .addStatement("\$T.logg()", "${Class.forName(("${Utils.class}"))}")

                        // otherClassNameList.size() > 0.addStatement((otherClassNameList.size() > 0 ? "${Class.forName(System.getProperty(otherClassNameList.get(0)))}" : "内容为空")) // 方法名列表

//                        .addStatement("${str},${otherPackageNameList}")    // 方法名列表
//                        .addStatement("${str},${otherPackageNameList}.${otherClassNameList}")    // 方法名列表
                        .endControlFlow()
                break
            case 1:
                otherClassMethodsAccessList.add("void")
                methodBuilder
                        .addCode("" + "int total = 0;\n" + "for (int i = 0; i < 10; i++) {\n" + "  total += i;\n" + "}\n")
//                        .addStatement("${str}(), "Class.forName(System.getProperty("FULL_NAME"))) // 方法名列表
//                        .addStatement((System.getProperty("FULL_NAME", fullName) != null ? "${str}AAAA(), ${Class.forName(System.getProperty("FULL_NAME", fullName))}" : "${str}()")) // 方法名列表
                        // .addStatement((otherClassNameList.size() > 0 ? "${otherClassNameList.get(0)}" : "内容为空")) // 方法名列表

                        // otherClassNameList.size() > 0.addStatement((otherClassNameList.size() > 0 ? "${Class.forName(System.getProperty(otherClassNameList.get(0)))}" : "内容为空")) // 方法名列表

//                        .addStatement("${str},${otherPackageNameList}")    // 方法名列表
//                        .addStatement("${str},${otherPackageNameList}.${otherClassNameList}")    // 方法名列表
                break
            case 2:
                otherClassMethodsAccessList.add("void")
                methodBuilder.beginControlFlow("try")
                        .addStatement("throw new Exception(\$S)", "Failed")
                        .nextControlFlow("catch (\$T e)", Exception.class)
//                        .addStatement((System.getProperty("FULL_NAME", fullName) != null ? "${str}AAA(), ${Class.forName(System.getProperty("FULL_NAME", fullName))}" : "${str}()")) // 方法名列表
                        // .addStatement((otherClassNameList.size() > 0 ? "${otherClassNameList.get(0)}" : "内容为空")) // 方法名列表


                        // otherClassNameList.size() > 0.addStatement((otherClassNameList.size() > 0 ? "${Class.forName(System.getProperty(otherClassNameList.get(0)))}" : "内容为空")) // 方法名列表

//                        .addStatement("${str},${otherPackageNameList}")    // 方法名列表
//                        .addStatement("${str},${otherPackageNameList}.${otherClassNameList}")    // 方法名列表

                        .endControlFlow()
                break
            case 3:
                methodBuilder
//                        .addStatement((System.getProperty("FULL_NAME", fullName) != null ? "${str}AAA(), ${Class.forName(System.getProperty("FULL_NAME", fullName))}" : "${str}()")) // 方法名列表
                        // .addStatement((otherClassNameList.size() > 0 ? "${otherClassNameList.get(0)}" : "内容为空")) // 方法名列表


                        // otherClassNameList.size() > 0.addStatement((otherClassNameList.size() > 0 ? "${Class.forName(System.getProperty(otherClassNameList.get(0)))}" : "内容为空")) // 方法名列表

//                        .addStatement("${str},${otherPackageNameList}")    // 方法名列表
//                        .addStatement("${str},${otherPackageNameList}.${otherClassNameList}")    // 方法名列表

                        .returns(Date.class)
                        .addStatement("return new \$T()", Date.class)
                break
            case 4:
                otherClassMethodsAccessList.add("public static void")
                methodBuilder.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
//                        .addStatement((System.getProperty("FULL_NAME", fullName) != null ? "${str}AAA(), ${Class.forName(System.getProperty("FULL_NAME", fullName))}" : "${str}()")) // 方法名列表
                        // .addStatement((otherClassNameList.size() > 0 ? "${otherClassNameList.get(0)}" : "内容为空")) // 方法名列表


                        // otherClassNameList.size() > 0.addStatement((otherClassNameList.size() > 0 ? "${Class.forName(System.getProperty(otherClassNameList.get(0)))}" : "内容为空")) // 方法名列表

//                        .addStatement("${str},${Class.forName(System.getProperty(otherClassNameList.get(0)))}")    // 方法名列表
//                        .addStatement("${str},${otherPackageNameList}")    // 方法名列表
//                        .addStatement("${str},${otherPackageNameList}.${otherClassNameList}")    // 方法名列表
                        .returns(void.class)
                        .addParameter(String[].class, "args")
                        .addStatement("\$T.out.println(\$S)", System.class, "Hello")

                break
            case 5:
                otherClassMethodsAccessList.add("public static void")
                methodBuilder.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
//                        .addStatement((System.getProperty("FULL_NAME", fullName) != null ? "${str}AAA(), ${Class.forName(System.getProperty("FULL_NAME", fullName))}" : "${str}()")) // 方法名列表
                        // .addStatement((otherClassNameList.size() > 0 ? "${otherClassNameList.get(0)}" : "内容为空")) // 方法名列表

//                        .addStatement((otherClassNameList.size() > 0 ? "${Class.forName(System.getProperty(otherClassNameList.get(0)))}" : "内容为空")) // 方法名列表

//                        .addStatement("${str},${otherPackageNameList}.${otherClassNameList}")    // 方法名列表

                break
            //todo：添加随机方法
            default:
                otherClassMethodsAccessList.add("public static void")
                methodBuilder.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
//                        .addStatement((System.getProperty("FULL_NAME", fullName) != null ? "${str}AAA(), ${Class.forName(System.getProperty("FULL_NAME", fullName))}" : "${str}()")) // 方法名列表
                        // .addStatement((otherClassNameList.size() > 0 ? "${otherClassNameList.get(0)}" : "内容为空")) // 方法名列表
                
                        
                        // otherClassNameList.size() > 0.addStatement((otherClassNameList.size() > 0 ? "${Class.forName(System.getProperty(otherClassNameList.get(0)))}" : "内容为空")) // 方法名列表

//                        .addStatement("${str},${otherPackageNameList}.${otherClassNameList}")    // 方法名列表
                        
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


    // 生成activity
    static List<String> generateActivity(File javaDir, File resDir, String namespace, String packageName, JunkCodeConfig config) {
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
                layoutName = layoutNameBuilder.toString()
                writeStringToFile(new File(resDir, "layout/${layoutName}.xml"), layoutContentBuilder.toString())
            } else {
//                def activityPreName = generateName(i)
                // todo：从固有列表中随机获取任意一个 Activity 名称
                def activityPreName = getRandomActivityName(i)
                className = activityPreName.capitalize() + "Activity"
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
                            methodName = generateRandomMethodsName(j)
//                            methodName = generateName(j)
                        }

                        stringNameList.add(methodName)
                        def methodBuilder = MethodSpec.methodBuilder(methodName)
                        if (config.methodGenerator) {
                            config.methodGenerator.execute(methodBuilder)
                        } else {
                            generateMethods(methodBuilder)
                        }
                        typeBuilder.addMethod(methodBuilder.build())
                        // 只添加没有参数的方法，且将新添加的数据放在首位
                        if (methodBuilder.build().parameters.size() == 0) {
                            stringList.add(methodBuilder.build().name)
                            otherClassMethodsNameList.add(0,methodBuilder.build().name)
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
                        .addModifiers(Modifier.PROTECTED)
                        .addStatement("super.onBackPressed()")
                        .build())


                def javaFile = JavaFile.builder(packageName, typeBuilder.build()).build()
                writeJavaToFile(javaDir, javaFile)
                activityList.add(packageName + "." + className)
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
//        otherClassNameList.clear()
        // config.otherCountPerPackage 获取设置的类数目大小
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
                        methodName = generateRandomMethodsName(j)
//                        methodName = generateName(j)
                    }
                    def methodBuilder = MethodSpec.methodBuilder(methodName)
                    if (config.methodGenerator) {
                        config.methodGenerator.execute(methodBuilder)
                    } else {
                        generateMethods(methodBuilder)
                    }
                    typeBuilder.addMethod(methodBuilder.build())
                    otherClassMethodsNameList.add(0,methodBuilder.build().name)
//                    otherClassMethodsNameList.removeLast()
                }
            }
            def javaFile = JavaFile.builder(packageName, typeBuilder.build()).build()
            otherPackageNameList.add(0, javaFile.packageName)
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


    // 随机生成一个Service名称
    static String generateServiceName(int index) {
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


    // 生成AndroidManifest.xml, todo: 顺序打乱, 读取 manifest文件,然后以数组的形式来将顺序打乱
    static void generateManifest(File manifestFile, List<String> activityList) {
        StringBuilder sb = new StringBuilder()
        sb.append("<manifest xmlns:android=\"http://schemas.android.com/apk/res/android\">\n")
//        sb.append("<uses-permission android:name=\"android.permission.INTERNET\" />\n")
        sb.append("    <application>\n")
        for (i in 0..<activityList.size()) {
            sb.append("        <activity android:name=\"${activityList.get(i)}\"  android:exported=\"false\"/>\n")
            sb.append("        <service android:name=\"${generateServiceName(i)}\"  android:exported=\"false\"/>\n")
//            sb.append("        <meta-data android:name=\"${generateName(i)}\"   android:value=\"oynestszvybpftwwjphxe\"\n")
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