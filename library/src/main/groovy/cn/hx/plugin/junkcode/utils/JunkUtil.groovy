package cn.hx.plugin.junkcode.utils

import cn.hx.plugin.junkcode.ext.JunkCodeConfig
import cn.hx.plugin.junkcode.template.ResTemplate
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec
import org.gradle.api.Project
import sun.rmi.runtime.Log

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
    static List<String> otherClassMethodsNameList = new ArrayList<>()
    static List<String> otherClassMethodsAccessList = new ArrayList<>()
    static myList = [key1: ['value1', 'value2', 'value3']]

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


    /**
     * 生成随机方法
     * @param methodBuilder
     */
    static void generateMethods(MethodSpec.Builder methodBuilder) {
        myList["${methodBuilder.parameters}"] = ["${methodBuilder.name}"]
        switch (random.nextInt(5)) {
            case 0:
                methodBuilder.addStatement("long now = \$T.currentTimeMillis()", System.class)
                        .beginControlFlow("if (\$T.currentTimeMillis() < now)", System.class)
                        .addStatement("\$T.out.println(\$S)", System.class, "Time travelling, woo hoo!")
                        .nextControlFlow("else if (\$T.currentTimeMillis() == now)", System.class)
                        .addStatement("\$T.out.println(\$S)", System.class, "Time stood still!")
                        .nextControlFlow("else")
                        .addStatement("\$T.out.println(\$S)", System.class, "Ok, time still moving forward")
                        .endControlFlow()
                break
            case 1:
                methodBuilder.addCode("" + "int total = 0;\n" + "for (int i = 0; i < 10; i++) {\n" + "  total += i;\n" + "}\n")
                break
            case 2:
                methodBuilder.beginControlFlow("try")
                        .addStatement("throw new Exception(\$S)", "Failed")
                        .nextControlFlow("catch (\$T e)", Exception.class)
                        .addStatement("throw new \$T(e)", RuntimeException.class)
                        .endControlFlow()
                break
            case 3:
                methodBuilder.returns(Date.class)
                        .addStatement("return new \$T()", Date.class)
                break
            case 4:
                methodBuilder.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .returns(void.class)
                        .addParameter(String[].class, "args")
                        .addStatement("\$T.out.println(\$S)", System.class, "Hello")
                break
            //todo：添加随机方法
            default:
                /*
                    public static void vpWfW(String[] strArr) {
                        System.out.println("Hello");
                    }
                 */
                methodBuilder.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .returns(void.class)
                        .addParameter(String[].class, "args")
                        .addStatement("\$T.out.println(\$S)", System.class, "Hello")
        }
    }

    /**
     * 生成颜色代码
     * @return
     */
    static String generateColor() {
        def sb = new StringBuilder()
        sb.append("#")
        for (i in 0..5) {
            sb.append(color[random.nextInt(color.size())])
        }
        return sb.toString()
    }



    /**
     * 生成id代码
     * @return
     */
    static String generateId() {
        def sb = new StringBuilder()
        for (i in 0..5) {
            sb.append(abc[random.nextInt(abc.size())])
        }
        return sb.toString()
    }

    /**
     * 生成Activity
     * @param javaDir
     * @param packageName
     * @param config
     */
    static List<String> generateActivity(File javaDir, File resDir, String namespace, String packageName, JunkCodeConfig config) {
        def activityList = new ArrayList()
        for (int i = 0; i < config.activityCountPerPackage; i++) {
            def className
            def layoutName
            if (config.activityCreator) {
                def activityNameBuilder = new StringBuilder()
                def layoutNameBuilder = new StringBuilder()
                def layoutContentBuilder = new StringBuilder()
                config.activityCreator.execute(new Tuple4(i, activityNameBuilder, layoutNameBuilder, layoutContentBuilder))
                className = activityNameBuilder.toString()
                layoutName = layoutNameBuilder.toString()
                writeStringToFile(new File(resDir, "layout/${layoutName}.xml"), layoutContentBuilder.toString())
            } else {
                def activityPreName = generateName(i)
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
                            methodName = generateName(j)
                        }

                        stringNameList.add(methodName)
                        def methodBuilder = MethodSpec.methodBuilder(methodName)
                        if (config.methodGenerator) {
                            config.methodGenerator.execute(methodBuilder)
                        } else {
                            generateMethods(methodBuilder)
                        }
                        typeBuilder.addMethod(methodBuilder.build())
                        // todo：将方法名添加到列表中，便于之后的调用，存在问题：存储的和生成的字符串不一致

                        if (methodBuilder.build().parameters.size() == 0) {
                            stringList.add(methodBuilder.build().name)
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
        otherClassNameList.clear()
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
            if (config.typeGenerator) {
                config.typeGenerator.execute(typeBuilder)
            } else {
                typeBuilder.addModifiers(Modifier.PUBLIC)
                otherClassMethodsNameList.clear()
                for (int j = 0; j < config.methodCountPerClass; j++) {
                    def methodName
                    if (config.methodNameCreator) {
                        def methodNameBuilder = new StringBuilder()
                        config.methodNameCreator.execute(new Tuple2(j, methodNameBuilder))
                        methodName = methodNameBuilder.toString()
                    } else {
                        methodName = generateName(j)
                    }
                    def methodBuilder = MethodSpec.methodBuilder(methodName)
                    if (config.methodGenerator) {
                        config.methodGenerator.execute(methodBuilder)
                    } else {
                        generateMethods(methodBuilder)
                    }

                    typeBuilder.addMethod(methodBuilder.build())
                    otherClassMethodsNameList.add(methodBuilder.build().name)
                }

                myList['key2'] = ['value4', 'value5', 'value6']
                typeBuilder.addMethod(otherClassNameList[0])
                typeBuilder.addMethod(otherClassNameList[1])
                typeBuilder.addMethod(otherClassNameList[2])
                typeBuilder.addMethod(otherClassNameList[3])
                typeBuilder.addMethod(otherClassNameList[4])
                typeBuilder.addMethod(otherClassMethodsNameList[0])
                typeBuilder.addMethod(otherClassMethodsNameList[1])
                typeBuilder.addMethod(otherClassMethodsNameList[2])
                typeBuilder.addMethod(otherClassMethodsNameList[3])
                typeBuilder.addMethod(otherClassMethodsNameList[4])
                typeBuilder.addMethod(myList)
                typeBuilder.addMethod(otherClassNameList)
                typeBuilder.addMethod(otherClassMethodsNameList)
            }
            def javaFile = JavaFile.builder(packageName, typeBuilder.build()).build()
            otherClassNameList.add(packageName)
            writeJavaToFile(javaDir, javaFile)
        }
    }

    /**
     * 生成layout文件
     * @param resDir
     * @param layoutName
     * @param config
     */
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

    /**
     * 生成drawable
     * @param resDir
     * @param config
     */
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

    /**
     * 生成strings.xml
     * @param resDir
     * @param config
     */
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


    /**
     * 生成AndroidManifest.xml
     * @param manifestFile
     * @param activityList
     */
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