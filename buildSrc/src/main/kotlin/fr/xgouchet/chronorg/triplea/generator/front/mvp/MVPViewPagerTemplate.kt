package fr.xgouchet.chronorg.triplea.generator.front.mvp

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.INT
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import fr.xgouchet.chronorg.triplea.TripleA
import fr.xgouchet.chronorg.triplea.generator.TemplateGenerator
import fr.xgouchet.chronorg.triplea.generator.TripleAPoet
import fr.xgouchet.chronorg.triplea.generator.TripleAPoet.Android.Support.FRAGMENTMGR
import fr.xgouchet.chronorg.triplea.model.Feature
import java.io.File

class MVPViewPagerTemplate : TemplateGenerator {

    companion object {
        const val TEMPLATE_VIEWPAGER = "viewpager"
    }

    private lateinit var feature: Feature
    private lateinit var packageName: String
    private lateinit var outputDir: File
    private lateinit var applicationId: String

    private lateinit var baseType: ClassName

    private lateinit var contractType: ClassName
    private lateinit var viewType: ClassName
    private lateinit var adapterType: ClassName
    private lateinit var dataSourceType: ParameterizedTypeName

    private lateinit var activityClassName: String
    private lateinit var fragmentClassName: String
    private lateinit var presenterClassName: String
    private lateinit var contractClassName: String
    private lateinit var adapterClassName: String
    private lateinit var viewHolderClassName: String
    private lateinit var dataSourceClassName: String

    private lateinit var pages: List<String>

    override fun canHandleTemplateType(template: String): Boolean {
        return template == TEMPLATE_VIEWPAGER
    }

    override fun generatateFeature(packageName: String,
                                   applicationId: String,
                                   feature: Feature,
                                   outputDir: File) {
        this.feature = feature
        this.packageName = packageName
        this.outputDir = outputDir
        this.applicationId = applicationId

        baseType = ClassName.bestGuess(feature.baseType.canonicalName)

        val key = feature.key.capitalize()
        activityClassName = "${TripleA.GENERATED_CLASS_PREFIX}$key${TripleAPoet.SUFFIX_ACTIVITY}"
        presenterClassName = "${TripleA.GENERATED_CLASS_PREFIX}$key${TripleAPoet.SUFFIX_PRESENTER}"
        fragmentClassName = "${TripleA.GENERATED_CLASS_PREFIX}$key${TripleAPoet.SUFFIX_FRAGMENT}"
        contractClassName = "${TripleA.GENERATED_CLASS_PREFIX}$key${TripleAPoet.SUFFIX_CONTRACT}"
        adapterClassName = "${TripleA.GENERATED_CLASS_PREFIX}$key${TripleAPoet.SUFFIX_PAGERADAPTER}"
        viewHolderClassName = "${TripleA.GENERATED_CLASS_PREFIX}$key${TripleAPoet.SUFFIX_VIEWHOLDER}"
        dataSourceClassName = "${TripleA.GENERATED_CLASS_PREFIX}$key${TripleAPoet.SUFFIX_DATASOURCE}"

        adapterType = ClassName.bestGuess("$packageName.$adapterClassName")

        pages = (feature.params["pages"] ?: "").split(";")

        generateMVPViewPagerActivity()
        generateMVPViewPagerAdapter()
    }

    private fun generateMVPViewPagerActivity() {

        val fileBuilder = FileSpec.builder(packageName, activityClassName)
                .addComment(TripleAPoet.HEADER_DISCLAIMER)

        val parentType = ParameterizedTypeName.get(ClassName.bestGuess(TripleA.Core.Front.MVP.BaseViewPagerActivity.CANONICAL_NAME), adapterType)

        val activity = TypeSpec.classBuilder(activityClassName)
                .superclass(parentType)
                .addModifiers(KModifier.ABSTRACT)

        // handleIntent
        activity.addFunction(
                FunSpec.builder("handleIntent")
                        .addModifiers(KModifier.OVERRIDE, KModifier.OPEN)
                        .addParameter("intent", TripleAPoet.Android.INTENT)
                        .build()
        )

        // getPresenterKey()
        activity.addFunction(
                FunSpec.builder("getAdapterKey")
                        .addModifiers(KModifier.OVERRIDE, KModifier.OPEN)
                        .returns(ClassName.bestGuess("kotlin.String"))
                        .addStatement("return \"${feature.key}\"")
                        .build()
        )

        // getFabIcon ?
        val fabIcon = feature.params["fab_icon"]
        if (fabIcon != null && fabIcon.isNotBlank()) {
            activity.addFunction(
                    FunSpec.builder("getFabIcon")
                            .addModifiers(KModifier.OPEN, KModifier.OVERRIDE)
                            .returns(INT)
                            .addStatement("return %T.drawable.$fabIcon", ClassName.bestGuess("$applicationId.R"))
                            .build()
            )
        }


        fileBuilder.addType(activity.build())
        fileBuilder.build().writeTo(outputDir)
    }


    private fun generateMVPViewPagerAdapter() {

        val fileBuilder = FileSpec.builder(packageName, adapterClassName)
                .addComment(TripleAPoet.HEADER_DISCLAIMER)

        val presenterArrayType = ParameterizedTypeName.get(TripleAPoet.Kotlin.ARRAY, TripleAPoet.CONTRACT_PRESENTER.asNullable())

        val typeBuilder = TypeSpec.classBuilder(adapterClassName)
                .superclass(TripleAPoet.PAGERADAPTER)
                .addModifiers(KModifier.ABSTRACT)
                .primaryConstructor(FunSpec.constructorBuilder()
                        .addParameter("fragmentManager", FRAGMENTMGR)
                        .build())
                .addSuperclassConstructorParameter("fragmentManager")

        val companionType = TypeSpec.companionObjectBuilder()
                .addProperty(PropertySpec.builder("PAGE_COUNT", INT)
                        .initializer("${pages.size}")
                        .addModifiers(KModifier.PRIVATE, KModifier.CONST)
                        .build())

        typeBuilder.addProperty(
                PropertySpec
                        .builder("presenters", presenterArrayType, KModifier.PROTECTED)
                        .initializer("arrayOfNulls(PAGE_COUNT)")
                        .build()
        )

        typeBuilder.addFunction(
                FunSpec.builder("getCount")
                        .returns(INT)
                        .addModifiers(KModifier.OVERRIDE)
                        .addStatement("return PAGE_COUNT")
                        .build()
        )

        val instantiateFragmentFun = FunSpec.builder("instantiateFragment")
                .addModifiers(KModifier.OVERRIDE, KModifier.FINAL)
                .addParameter("position", INT)
                .returns(TripleAPoet.Android.Support.FRAGMENT)
                .beginControlFlow("return when(position)")

        val instantiatePresenterFun = FunSpec.builder("instantiatePresenter")
                .addModifiers(KModifier.OVERRIDE, KModifier.FINAL)
                .addParameter("activity", TripleAPoet.Android.ACTIVITY)
                .addParameter("position", INT)
                .returns(TripleAPoet.CONTRACT_PRESENTER)
                .beginControlFlow("return when(position)")

        for ((i, page) in pages.withIndex()) {
            companionType.addProperty(PropertySpec.builder("PAGE_${page.toUpperCase()}", INT)
                    .initializer("$i")
                    .addModifiers(KModifier.PROTECTED, KModifier.CONST)
                    .build())

            typeBuilder.addFunction(
                    FunSpec.builder("instantiate${page.capitalize()}Fragment")
                            .addModifiers(KModifier.ABSTRACT)
                            .returns(TripleAPoet.Android.Support.FRAGMENT)
                            .build()
            )

            typeBuilder.addFunction(
                    FunSpec.builder("instantiate${page.capitalize()}Presenter")
                            .addModifiers(KModifier.ABSTRACT)
                            .addParameter("activity", TripleAPoet.Android.ACTIVITY)
                            .returns(TripleAPoet.CONTRACT_PRESENTER)
                            .build()
            )

            instantiatePresenterFun.addStatement("PAGE_${page.toUpperCase()} -> instantiate${page.capitalize()}Presenter(activity)")
            instantiateFragmentFun.addStatement("PAGE_${page.toUpperCase()} -> instantiate${page.capitalize()}Fragment()")
        }

        typeBuilder.addFunction(instantiatePresenterFun
                .addStatement("else -> throw %T(\"$adapterClassName can't handle position \$position\")", ClassName.bestGuess("kotlin.IllegalArgumentException"))
                .endControlFlow()
                .build())

        typeBuilder.addFunction(instantiateFragmentFun
                .addStatement("else -> throw %T(\"$adapterClassName can't handle position \$position\")", ClassName.bestGuess("kotlin.IllegalArgumentException"))
                .endControlFlow()
                .build())

        typeBuilder.addType(companionType.build())

        fileBuilder.addType(typeBuilder.build())
        fileBuilder.build().writeTo(outputDir)
    }
}