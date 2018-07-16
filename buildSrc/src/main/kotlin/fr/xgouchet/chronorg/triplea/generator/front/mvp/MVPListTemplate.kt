package fr.xgouchet.chronorg.triplea.generator.front.mvp

import com.squareup.kotlinpoet.BOOLEAN
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.INT
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.UNIT
import fr.xgouchet.chronorg.triplea.TripleA
import fr.xgouchet.chronorg.triplea.generator.TemplateGenerator
import fr.xgouchet.chronorg.triplea.generator.TripleAPoet
import fr.xgouchet.chronorg.triplea.model.Feature
import java.io.File

class MVPListTemplate : TemplateGenerator {

    companion object {
        const val TEMPLATE_LIST = "list"
    }

    private lateinit var feature: Feature
    private lateinit var packageName: String
    private lateinit var outputDir: File
    private lateinit var applicationId: String

    private lateinit var baseType: ClassName
    private lateinit var baseListType: ParameterizedTypeName
    private lateinit var baseMutableListType: ParameterizedTypeName

    private lateinit var contractType: ClassName
    private lateinit var viewType: ClassName
    private lateinit var presenterType: ClassName
    private lateinit var dataSourceType: ParameterizedTypeName

    private lateinit var activityClassName: String
    private lateinit var fragmentClassName: String
    private lateinit var presenterClassName: String
    private lateinit var contractClassName: String
    private lateinit var adapterClassName: String
    private lateinit var viewHolderClassName: String
    private lateinit var dataSourceClassName: String

    override fun canHandleTemplateType(template: String): Boolean {
        return template == TEMPLATE_LIST
    }

    override fun generatateFeature(packageName: String, applicationId: String, feature: Feature, outputDir: File) {
        this.feature = feature
        this.packageName = packageName
        this.outputDir = outputDir
        this.applicationId = applicationId

        baseType = ClassName.bestGuess(feature.baseType.canonicalName)
        baseListType = ParameterizedTypeName.get(TripleAPoet.Kotlin.LIST, baseType)
        baseMutableListType = ParameterizedTypeName.get(TripleAPoet.Kotlin.MUTABLELIST, baseType)

        val key = feature.key.capitalize()
        activityClassName = "${TripleA.GENERATED_CLASS_PREFIX}$key${TripleAPoet.SUFFIX_ACTIVITY}"
        presenterClassName = "${TripleA.GENERATED_CLASS_PREFIX}$key${TripleAPoet.SUFFIX_PRESENTER}"
        fragmentClassName = "${TripleA.GENERATED_CLASS_PREFIX}$key${TripleAPoet.SUFFIX_FRAGMENT}"
        contractClassName = "${TripleA.GENERATED_CLASS_PREFIX}$key${TripleAPoet.SUFFIX_CONTRACT}"
        adapterClassName = "${TripleA.GENERATED_CLASS_PREFIX}$key${TripleAPoet.SUFFIX_ADAPTER}"
        viewHolderClassName = "${TripleA.GENERATED_CLASS_PREFIX}$key${TripleAPoet.SUFFIX_VIEWHOLDER}"
        dataSourceClassName = "${TripleA.GENERATED_CLASS_PREFIX}$key${TripleAPoet.SUFFIX_DATASOURCE}"

        contractType = ClassName.bestGuess("$packageName.$contractClassName")
        viewType = contractType.nestedClass(TripleAPoet.SUFFIX_VIEW)
        presenterType = contractType.nestedClass(TripleAPoet.SUFFIX_PRESENTER)
        dataSourceType = ParameterizedTypeName.get(TripleAPoet.DATASOURCE, baseListType)

        if (feature.params["no_activity"]?.toBoolean() != true) {
            generateMVPListActivity()
        }

        generateMVPListContract()
        generateMVPListPresenter()
        generateMVPListFragment()
        generateMVPListAdapter()
        generateMVPListViewHolder()
        generateMVPListDataSource()
    }


    private fun generateMVPListActivity() {

        val fileBuilder = FileSpec.builder(packageName, activityClassName)
                .addComment(TripleAPoet.HEADER_DISCLAIMER)

        val parentType = ParameterizedTypeName.get(ClassName.bestGuess(TripleA.Core.Front.MVP.BaseActivity.CANONICAL_NAME),
                presenterType, ClassName.bestGuess("$packageName.$fragmentClassName")
        )

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

    private fun generateMVPListContract() {

        val fileBuilder = FileSpec.builder(packageName, contractClassName)
                .addComment(TripleAPoet.HEADER_DISCLAIMER)


        val presenterBuilder = TypeSpec.interfaceBuilder(TripleAPoet.SUFFIX_PRESENTER)
                .addSuperinterface(ParameterizedTypeName.get(TripleAPoet.LISTCONTRACT_PRESENTER, baseType))
        if (feature.params["refreshable"]?.toBoolean() == true) {
            presenterBuilder.addFunction(FunSpec.builder("onRefresh").addModifiers(KModifier.ABSTRACT).build())
        }

        val viewBuilder = TypeSpec.interfaceBuilder(TripleAPoet.SUFFIX_VIEW)
                .addSuperinterface(ParameterizedTypeName.get(TripleAPoet.LISTCONTRACT_VIEW, baseType))

        val contractBuilder = TypeSpec.interfaceBuilder(contractClassName)
                .addType(presenterBuilder.build())
                .addType(viewBuilder.build())


        fileBuilder.addType(contractBuilder.build())
        fileBuilder.build().writeTo(outputDir)
    }

    private fun generateMVPListPresenter() {

        val fileBuilder = FileSpec.builder(packageName, presenterClassName)
                .addComment(TripleAPoet.HEADER_DISCLAIMER)

        val typeBuilder = TypeSpec.classBuilder(presenterClassName)
                .addSuperinterface(presenterType)
                .addModifiers(KModifier.OPEN)
                .primaryConstructor(FunSpec.constructorBuilder()
                        .addParameter("dataSource", dataSourceType)
                        .build())
                .addProperty(PropertySpec.builder("dataSource", dataSourceType, KModifier.PRIVATE).initializer("dataSource").build())
                .addProperty(PropertySpec.varBuilder("view", viewType.asNullable(), KModifier.PRIVATE).initializer("null").build())
                .addProperty(PropertySpec.varBuilder("data", baseListType.asNullable(), KModifier.INTERNAL).initializer("null").build())

        // onViewAttached
        typeBuilder.addFunction(
                FunSpec.builder("onViewAttached")
                        .addParameter("v", TripleAPoet.CONTRACT_VIEW)
                        .addParameter("isRestored", BOOLEAN)
                        .addModifiers(KModifier.OVERRIDE)
                        .addStatement("require(v is %T)", viewType)
                        .addStatement("view = v as %T", viewType)
                        .addStatement("v.setPresenter(this)")
                        .addStatement("val restoredData = data")
                        .beginControlFlow("if (isRestored && restoredData != null)")
                        .addStatement("onDataLoaded(restoredData)")
                        .nextControlFlow("else")
                        .addStatement("loadData()")
                        .endControlFlow()
                        .build()
        )

        // onViewDetached
        typeBuilder.addFunction(
                FunSpec.builder("onViewDetached")
                        .addModifiers(KModifier.OVERRIDE)
                        .addStatement("dataSource.cancel()")
                        .build()
        )

        // getKey()
        typeBuilder.addFunction(
                FunSpec.builder("getKey")
                        .addModifiers(KModifier.OVERRIDE, KModifier.OPEN)
                        .returns(ClassName.bestGuess("kotlin.String"))
                        .addStatement("return \"${feature.key}\"")
                        .build()
        )


        // onEntitySelected
        typeBuilder.addFunction(
                FunSpec.builder("onEntitySelected")
                        .addParameter("entity", baseType)
                        .addModifiers(KModifier.OVERRIDE, KModifier.OPEN)
                        .addStatement("view?.navigateToEntity(entity)")
                        .build()
        )

        if (feature.params["refreshable"]?.toBoolean() == true) {
            typeBuilder.addFunction(
                    FunSpec.builder("onRefresh")
                            .addModifiers(KModifier.OVERRIDE, KModifier.OPEN)
                            .addStatement("loadData()")
                            .build()
            )
        }

        // Load data
        typeBuilder.addFunction(
                FunSpec.builder("loadData")
                        .addModifiers(KModifier.PROTECTED, KModifier.OPEN)
                        .addStatement("view?.showLoading()")
                        .addStatement("dataSource.getData({ onDataLoaded(it) }, { onErrorLoadingData(it) })")
                        .build()
        )

        // Callbacks
        typeBuilder.addFunction(
                FunSpec.builder("onDataLoaded")
                        .addParameter("receivedData", baseListType)
                        .addModifiers(KModifier.PROTECTED, KModifier.OPEN)
                        .addStatement("data = receivedData")
                        .addStatement("view?.showEntities(receivedData)")
                        .build()
        )
        typeBuilder.addFunction(
                FunSpec.builder("onErrorLoadingData")
                        .addParameter("throwable", TripleAPoet.Kotlin.THROWABLE)
                        .addModifiers(KModifier.PROTECTED, KModifier.OPEN)
                        .addStatement("view?.showError(throwable)")
                        .build()
        )

        fileBuilder.addType(typeBuilder.build())
        fileBuilder.build().writeTo(outputDir)
    }


    private fun generateMVPListFragment() {

        val fileBuilder = FileSpec.builder(packageName, fragmentClassName)
                .addComment(TripleAPoet.HEADER_DISCLAIMER)

        val typeBuilder = TypeSpec.classBuilder(fragmentClassName)
                .superclass(TripleAPoet.Android.Support.FRAGMENT)
                .addSuperinterface(viewType)
                .addModifiers(KModifier.ABSTRACT)
                .addProperty(PropertySpec.varBuilder("presenter", presenterType, KModifier.PROTECTED, KModifier.LATEINIT).build())
                .addProperty(PropertySpec.builder("adapter", ClassName.bestGuess("$packageName.$adapterClassName"), KModifier.PROTECTED, KModifier.ABSTRACT).build())
                .addProperty(PropertySpec.varBuilder("contentView", TripleAPoet.Android.VIEW.asNullable(), KModifier.PROTECTED).initializer("null").build())
                .addProperty(PropertySpec.varBuilder("recyclerView", TripleAPoet.Android.Support.RECYCLERVIEW.asNullable(), KModifier.PROTECTED).initializer("null").build())
                .addProperty(PropertySpec.varBuilder("swipeRefreshLayout", TripleAPoet.Android.Support.SWIPEREFRESHLAYOUT.asNullable(), KModifier.PROTECTED).initializer("null").build())
                .addProperty(PropertySpec.builder("layoutId", INT, KModifier.PROTECTED, KModifier.OPEN)
                        .initializer("%T.layout.fragment_list", TripleAPoet.R)
                        .addAnnotation(TripleAPoet.Android.Support.LAYOUTRES)
                        .build())

        if (feature.params["empty_state"]?.toBoolean() == true) {
            typeBuilder.addProperty(PropertySpec.varBuilder("emptyView", TripleAPoet.Android.VIEW.asNullable(), KModifier.PROTECTED).initializer("null").build())
        }

        // setPresenter
        val setPresenterFun = FunSpec.builder("setPresenter")
                .addParameter("p", TripleAPoet.CONTRACT_PRESENTER)
                .addModifiers(KModifier.OVERRIDE)
                .addStatement("require(p is %T)", presenterType)
                .addStatement("presenter = p as %T", presenterType)
        typeBuilder.addFunction(setPresenterFun.build())

        // onCreate
        val onCreateViewFun = FunSpec.builder("onCreateView")
                .addModifiers(KModifier.OVERRIDE)
                .addParameter("layoutInflater", TripleAPoet.Android.LAYOUTINFLATER)
                .addParameter("container", ClassName.bestGuess("android.view.ViewGroup").asNullable())
                .addParameter("savedInstanceState", ClassName.bestGuess("android.os.Bundle").asNullable())
                .returns(TripleAPoet.Android.VIEW.asNullable())
                .addStatement("val rootView = layoutInflater.inflate(layoutId, container, false)")
                .addStatement("contentView = rootView", TripleAPoet.R)
                .addStatement("swipeRefreshLayout = rootView.findViewById(%T.id.refresh)", TripleAPoet.R)

        if (feature.params["refreshable"]?.toBoolean() == true) {
            onCreateViewFun.addStatement("swipeRefreshLayout?.setOnRefreshListener { presenter.onRefresh() }")
        } else {
            onCreateViewFun.addStatement("swipeRefreshLayout?.isEnabled = false")
        }
        if (feature.params["empty_state"]?.toBoolean() == true) {
            onCreateViewFun.addStatement("emptyView = rootView.findViewById(%T.id.empty)", TripleAPoet.R)
        }
        onCreateViewFun.addStatement("recyclerView = rootView.findViewById(%T.id.recycler)", TripleAPoet.R)
                .addStatement("recyclerView?.adapter = adapter")
                .addStatement("recyclerView?.layoutManager = %T(context)", TripleAPoet.Android.Support.LINEARLAYOUTMGR)
        onCreateViewFun.addStatement("return rootView")
        typeBuilder.addFunction(onCreateViewFun.build())

        // showEntities
        val showEntitiesFun = FunSpec.builder("showEntities")
                .addParameter("entities", baseListType)
                .addModifiers(KModifier.OVERRIDE, KModifier.OPEN)
                .addStatement("adapter.update(entities)")
                .addStatement("swipeRefreshLayout?.isRefreshing = false")
        typeBuilder.addFunction(showEntitiesFun.build())

        // showLoading
        typeBuilder.addFunction(
                FunSpec.builder("showLoading")
                        .addModifiers(KModifier.OVERRIDE, KModifier.OPEN)
                        .addStatement("swipeRefreshLayout?.isRefreshing = true")
                        .build()
        )


        // ShowError
        val showErrorFun = FunSpec.builder("showError")
                .addParameter("throwable", TripleAPoet.Kotlin.THROWABLE)
                .addModifiers(KModifier.OVERRIDE, KModifier.OPEN)
                .addStatement("val messageId = getErrorMessage(throwable)")
                .addStatement("swipeRefreshLayout?.isRefreshing = false")
        showErrorFun.beginControlFlow("contentView?.let")
                .beginControlFlow("if (messageId == 0)")
                .addStatement("%T.make(it, \"An unknown error occurred ‽\", %T.LENGTH_LONG).show();", TripleAPoet.Android.Support.SNACKBAR, TripleAPoet.Android.Support.SNACKBAR)
                .nextControlFlow("else")
                .addStatement("%T.make(it, messageId, %T.LENGTH_LONG).show();", TripleAPoet.Android.Support.SNACKBAR, TripleAPoet.Android.Support.SNACKBAR)
                .endControlFlow()
                .endControlFlow()
        typeBuilder.addFunction(showErrorFun.build())

        typeBuilder.addFunction(
                FunSpec.builder("getErrorMessage")
                        .addParameter("throwable", TripleAPoet.Kotlin.THROWABLE)
                        .returns(INT)
                        .addAnnotation(TripleAPoet.Android.Support.STRINGRES)
                        .addModifiers(KModifier.PROTECTED, KModifier.OPEN)
                        .addStatement("return 0")
                        .build()
        )

        fileBuilder.addType(typeBuilder.build())
        fileBuilder.build().writeTo(outputDir)
    }

    private fun generateMVPListAdapter() {

        val vhType = ClassName.bestGuess("$packageName.$viewHolderClassName")
        val rvType = ParameterizedTypeName.get(TripleAPoet.Android.Support.RECYCLERVIEW.nestedClass("Adapter"), vhType)

        val listenerType = LambdaTypeName.get(parameters = *arrayOf(baseType), returnType = UNIT)

        val fileBuilder = FileSpec.builder(packageName, adapterClassName)
                .addComment(TripleAPoet.HEADER_DISCLAIMER)

        val typeBuilder = TypeSpec.classBuilder(adapterClassName)
                .superclass(rvType)
                .addModifiers(KModifier.ABSTRACT)
                .primaryConstructor(FunSpec.constructorBuilder()
                        .addParameter("listener", listenerType.asNullable())
                        .build())
                .addProperty(PropertySpec.builder("data", baseMutableListType, KModifier.PROTECTED).initializer("mutableListOf()").build())
                .addProperty(PropertySpec.builder("listener", listenerType.asNullable()).addModifiers(KModifier.PRIVATE).initializer("listener").build())

        // getItemViewType
        if (feature.params["single_layout"]?.toBoolean() == true) {
            if (feature.params["databinding"]?.toBoolean() != true) {
                typeBuilder.addProperty(PropertySpec.builder("layoutId", INT, KModifier.PROTECTED, KModifier.ABSTRACT).build())
            }
        } else {
            typeBuilder.addFunction(
                    FunSpec.builder("getItemViewType")
                            .addModifiers(KModifier.OVERRIDE, KModifier.OPEN)
                            .addParameter("position", INT)
                            .returns(INT)
                            .addStatement("val item = data.getOrNull(position) ?: return -1")
                            .addStatement("return getItemViewType(item)")
                            .build()
            )

            typeBuilder.addFunction(
                    FunSpec.builder("getItemViewType")
                            .addModifiers(KModifier.ABSTRACT)
                            .addParameter("item", baseType)
                            .returns(INT)
                            .build()
            )
            if (feature.params["databinding"]?.toBoolean() != true) {
                typeBuilder.addFunction(
                        FunSpec.builder("getLayoutId")
                                .addModifiers(KModifier.ABSTRACT)
                                .addAnnotation(TripleAPoet.Android.Support.LAYOUTRES)
                                .addParameter("viewType", INT)
                                .returns(INT)
                                .build()
                )
            }
        }


        // getItemCount
        typeBuilder.addFunction(
                FunSpec.builder("getItemCount")
                        .addModifiers(KModifier.OVERRIDE, KModifier.OPEN)
                        .returns(INT)
                        .addStatement("return data.size")
                        .build()
        )

        //onCreateViewHolder
        val onCreateVHFun = FunSpec.builder("onCreateViewHolder")
                .addModifiers(KModifier.OVERRIDE, KModifier.OPEN)
                .addParameter("parent", TripleAPoet.Android.VIEWGROUP)
                .addParameter("viewType", INT)
                .returns(vhType)
        if (feature.params["databinding"]?.toBoolean() == true) {
            onCreateVHFun
                    .addStatement("val layoutInflater = %T.from(parent.context)", TripleAPoet.Android.LAYOUTINFLATER)
                    .addStatement("val binding = inflateDataBinding(layoutInflater, parent, viewType) ?: throw IllegalStateException(\"Unable to inflate view\")")
                    .addStatement("return instantiateViewHolder(binding, viewType)", vhType)
        } else {
            if (feature.params["single_layout"]?.toBoolean() != true) {
                onCreateVHFun.addStatement("val layoutId = getLayoutId(viewType)")
            }
            onCreateVHFun.addStatement("val view = %T.from(parent.context).inflate(layoutId, parent, false)", TripleAPoet.Android.LAYOUTINFLATER)
                    .addStatement("return instantiateViewHolder(view, viewType)", vhType)

        }
        typeBuilder.addFunction(onCreateVHFun.build())

        // onBindViewHolder
        typeBuilder.addFunction(
                FunSpec.builder("onBindViewHolder")
                        .addModifiers(KModifier.OVERRIDE, KModifier.OPEN)
                        .addParameter("holder", vhType)
                        .addParameter("position", INT)
                        .addStatement("val item = data.getOrNull(position) ?: return ")
                        .addStatement("holder.bindItem(item)")
                        .build()
        )

        // Update data
        typeBuilder.addFunction(
                FunSpec.builder("update")
                        .addParameter("newData", baseListType)
                        .addStatement("val diffCallback = getDiffCallback(data, newData)")
                        .beginControlFlow("if (diffCallback == null)")
                        .addStatement("data.clear()")
                        .addStatement("data.addAll(newData)")
                        .addStatement("notifyDataSetChanged()")
                        .nextControlFlow("else")
                        .addStatement(" val diffResult = %T.calculateDiff(diffCallback)", TripleAPoet.Android.Support.DIFFUTIL)
                        .addStatement("data.clear()")
                        .addStatement("data.addAll(newData)")
                        .addStatement("diffResult.dispatchUpdatesTo(this)")
                        .endControlFlow()
                        .build()
        )
        // Abstracts
        typeBuilder.addFunction(
                FunSpec.builder("getDiffCallback")
                        .addParameter("oldData", baseListType)
                        .addParameter("newData", baseListType)
                        .addModifiers(KModifier.OPEN, KModifier.PROTECTED)
                        .returns(TripleAPoet.Android.Support.DIFFUTIL_CALLBACK.asNullable())
                        .addStatement("return null")
                        .build())


        if (feature.params["databinding"]?.toBoolean() == true) {
            typeBuilder.addFunction(
                    FunSpec.builder("inflateDataBinding")
                            .addModifiers(KModifier.ABSTRACT)
                            .addParameter("layoutInflater", TripleAPoet.Android.LAYOUTINFLATER)
                            .addParameter("parent", TripleAPoet.Android.VIEWGROUP)
                            .addParameter("viewType", INT)
                            .returns(TripleAPoet.Android.VIEWDATABINDING.asNullable())
                            .build()
            )
            val instantiateVHFun = FunSpec.builder("instantiateViewHolder")
                    .addParameter("binding", TripleAPoet.Android.VIEWDATABINDING)
                    .addParameter("viewType", INT)
                    .returns(vhType)
            if (feature.params["single_layout"]?.toBoolean() == true) {
                instantiateVHFun.addModifiers(KModifier.OPEN)
                        .addStatement("return %T(binding, listener)", vhType)
            } else {
                instantiateVHFun.addModifiers(KModifier.ABSTRACT)
            }

            typeBuilder.addFunction(instantiateVHFun.build())
        } else {
            typeBuilder.addFunction(
                    FunSpec.builder("instantiateViewHolder")
                            .addModifiers(KModifier.ABSTRACT)
                            .addParameter("view", TripleAPoet.Android.VIEW)
                            .addParameter("viewType", INT)
                            .returns(vhType)
                            .build()
            )
        }

        fileBuilder.addType(typeBuilder.build())
        fileBuilder.build().writeTo(outputDir)
    }

    private fun generateMVPListViewHolder() {
        val listenerType = LambdaTypeName.get(parameters = *arrayOf(baseType), returnType = UNIT)
        val fileBuilder = FileSpec.builder(packageName, viewHolderClassName)
                .addComment(TripleAPoet.HEADER_DISCLAIMER)

        val typeBuilder = TypeSpec.classBuilder(viewHolderClassName)
                .superclass(TripleAPoet.Android.Support.RECYCLERVIEW.nestedClass("ViewHolder"))
                .addProperty(PropertySpec.varBuilder("item", baseType).addModifiers(KModifier.LATEINIT, KModifier.PROTECTED).build())
                .addProperty(PropertySpec.builder("listener", listenerType.asNullable()).addModifiers(KModifier.PRIVATE).initializer("listener").build())

        if (feature.params["databinding"]?.toBoolean() == true) {
            typeBuilder
                    .primaryConstructor(
                            FunSpec.constructorBuilder()
                                    .addParameter("binding", TripleAPoet.Android.VIEWDATABINDING)
                                    .addParameter("listener", listenerType.asNullable())
                                    .build())
                    .addProperty(PropertySpec.builder("binding", TripleAPoet.Android.VIEWDATABINDING, KModifier.PROTECTED).initializer("binding").build())
                    .addSuperclassConstructorParameter("binding.root")
                    .addModifiers(KModifier.OPEN)

            typeBuilder.addInitializerBlock(
                    CodeBlock.builder()
                            .beginControlFlow("if (listener != null)")
                            .addStatement("binding.root.setOnClickListener({ _ -> listener.invoke(item) })")
                            .endControlFlow()
                            .build()
            )
        } else {
            typeBuilder.primaryConstructor(FunSpec.constructorBuilder()
                    .addParameter("itemView", TripleAPoet.Android.VIEW)
                    .addParameter("listener", listenerType.asNullable())
                    .build())
                    .addSuperclassConstructorParameter("itemView")
                    .addModifiers(KModifier.ABSTRACT)

            typeBuilder.addInitializerBlock(
                    CodeBlock.builder()
                            .beginControlFlow("if (listener != null)")
                            .addStatement("itemView.setOnClickListener({ view -> listener.invoke(item) })")
                            .endControlFlow()
                            .build()
            )
        }

        // bindItem
        typeBuilder.addFunction(
                FunSpec.builder("bindItem")
                        .addParameter("item", baseType)
                        .addStatement("this.item = item")
                        .addStatement("onBindItem(item)")
                        .build()
        )
        val onBindItemFun = FunSpec.builder("onBindItem")
        if (feature.params["databinding"]?.toBoolean() == true
                && !feature.params["databinding_key"].isNullOrBlank()) {
            onBindItemFun.addModifiers(KModifier.OPEN)
                    .addParameter("item", baseType)
                    .addStatement("binding.setVariable(%T.${feature.params["databinding_key"]}, item)", ClassName.bestGuess("$applicationId.BR"))
                    .addStatement("binding.executePendingBindings()")
        } else {
            onBindItemFun.addModifiers(KModifier.ABSTRACT)
                    .addParameter("item", baseType)
        }
        typeBuilder.addFunction(onBindItemFun.build())

        fileBuilder.addType(typeBuilder.build())
        fileBuilder.build().writeTo(outputDir)
    }


    private fun generateMVPListDataSource() {
        val successType = LambdaTypeName.get(parameters = *arrayOf(baseListType), returnType = UNIT)
        val errorType = LambdaTypeName.get(parameters = *arrayOf(TripleAPoet.Kotlin.THROWABLE), returnType = UNIT)

        val fileBuilder = FileSpec.builder(packageName, dataSourceClassName)
                .addComment(TripleAPoet.HEADER_DISCLAIMER)

        val typeBuilder = TypeSpec.classBuilder(dataSourceClassName)
                .addSuperinterface(dataSourceType)
                .addModifiers(KModifier.ABSTRACT)

        if (feature.params["functional"] == "rx2") {

            val observableType = ParameterizedTypeName.get(ClassName.bestGuess("io.reactivex.Observable"), baseListType)

            typeBuilder
                    .addProperty(
                            PropertySpec.varBuilder("disposable", ClassName.bestGuess("io.reactivex.disposables.Disposable").asNullable(), KModifier.PRIVATE)
                                    .initializer("null")
                                    .build())

            typeBuilder.addFunction(
                    FunSpec.builder("getData")
                            .addModifiers(KModifier.OVERRIDE, KModifier.OPEN)
                            .addParameter("onSuccess", successType)
                            .addParameter("onError", errorType)
                            .addStatement("disposable?.dispose()")
                            .addStatement("disposable = observeData()")
                            .addStatement(".subscribeOn(%T.computation())", ClassName.bestGuess("io.reactivex.schedulers.Schedulers"))
                            .addStatement(".observeOn(%T.mainThread())", ClassName.bestGuess("io.reactivex.android.schedulers.AndroidSchedulers"))
                            .addStatement(".subscribe(onSuccess, onError)")
                            .build()
            )

            typeBuilder.addFunction(
                    FunSpec.builder("cancel")
                            .addModifiers(KModifier.OVERRIDE, KModifier.OPEN)
                            .addStatement("disposable?.dispose()")
                            .addStatement("disposable = null")
                            .build()
            )

            typeBuilder.addFunction(
                    FunSpec.builder("observeData")
                            .addModifiers(KModifier.ABSTRACT)
                            .returns(observableType)
                            .build()
            )
        }

        fileBuilder.addType(typeBuilder.build())
        fileBuilder.build().writeTo(outputDir)
    }
}