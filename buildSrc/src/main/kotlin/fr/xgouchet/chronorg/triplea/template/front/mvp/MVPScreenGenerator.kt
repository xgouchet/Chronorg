package fr.xgouchet.chronorg.triplea.template.front.mvp

import com.squareup.kotlinpoet.BOOLEAN
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.INT
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import fr.xgouchet.chronorg.triplea.TripleA
import fr.xgouchet.chronorg.triplea.model.Screen
import fr.xgouchet.chronorg.triplea.template.LayerGenerator
import java.io.File


class MVPScreenGenerator : LayerGenerator {

    companion object {
        const val LAYER_MVP = "front-mvp"

        const val SUFFIX_ACTIVITY = "Activity"
        const val SUFFIX_CONTRACT = "Contract"
        const val SUFFIX_PRESENTER = "Presenter"
        const val SUFFIX_VIEW = "View"
        const val SUFFIX_FRAGMENT = "Fragment"
        const val SUFFIX_ADAPTER = "Adapter"
        const val SUFFIX_VIEWHOLDER = "ViewHolder"

        // TRIPLEA TYPES
        private val DATASOURCE_TYPE = ClassName.bestGuess(TripleA.Core.Source.DataSource.CANONICAL_NAME)

        private val CONTRACT_VIEW_TYPE = ClassName.bestGuess(TripleA.Core.Front.MVP.BaseContract.View.CANONICAL_NAME)
        private val CONTRACT_PRESENTER_TYPE = ClassName.bestGuess(TripleA.Core.Front.MVP.BaseContract.Presenter.CANONICAL_NAME)

        private val LISTCONTRACT_VIEW_TYPE = ClassName.bestGuess(TripleA.Core.Front.MVP.BaseListContract.View.CANONICAL_NAME)
        private val LISTCONTRACT_PRESENTER_TYPE = ClassName.bestGuess(TripleA.Core.Front.MVP.BaseListContract.Presenter.CANONICAL_NAME)

        private val R_TYPE = ClassName.bestGuess(TripleA.Core.R)

        // KOTLIN TYPES
        private val LIST_TYPE = ClassName.bestGuess("kotlin.collections.List")
        private val MUTABLELIST_TYPE = ClassName.bestGuess("kotlin.collections.MutableList")

        // ANDROID TYPES
        private val ANDROID_VIEW_TYPE = ClassName.bestGuess("android.view.View")
        private val VIEWGROUP_TYPE = ClassName.bestGuess("android.view.ViewGroup")
        private val LAYOUTINFLATER_TYPE = ClassName.bestGuess("android.view.LayoutInflater")
        private val INTENT_TYPE = ClassName.bestGuess("android.content.Intent")

        private val RECYCLERVIEW_TYPE = ClassName.bestGuess("android.support.v7.widget.RecyclerView")
        private val LINEARLAYOUTMGR_TYPE = ClassName.bestGuess("android.support.v7.widget.LinearLayoutManager")
        private val DIFFUTIL_TYPE = ClassName.bestGuess("android.support.v7.util.DiffUtil")
        private val DIFFUTIL_CALLBACK_TYPE = DIFFUTIL_TYPE.nestedClass("Callback")
        private val VIEWDATABINDING_TYPE = ClassName.bestGuess("android.databinding.ViewDataBinding")
        private val SNACKBAR_TYPE = ClassName.bestGuess("android.support.design.widget.Snackbar")

        private val LAYOUTRES_TYPE = ClassName.bestGuess("android.support.annotation.LayoutRes")
        private val STRINGRES_TYPE = ClassName.bestGuess("android.support.annotation.StringRes")
        private val FRAGMENT_TYPE = ClassName.bestGuess("android.support.v4.app.Fragment")
        private val SWIPEREFRESHLAYOUT_TYPE = ClassName.bestGuess("android.support.v4.widget.SwipeRefreshLayout")
    }

    private lateinit var screen: Screen
    private lateinit var packageName: String
    private lateinit var outputDir: File

    private lateinit var baseType: ClassName
    private lateinit var baseListType: ParameterizedTypeName
    private lateinit var baseMutableListType: ParameterizedTypeName
    private lateinit var contractType: ClassName

    private lateinit var viewType: ClassName
    private lateinit var presenterType: ClassName
    private lateinit var dataSourceType: ParameterizedTypeName

    override fun canHandleLayerType(layer: String): Boolean {
        return layer.equals(LAYER_MVP, true)
    }

    override fun generatateScreen(layer: String,
                                  screen: Screen,
                                  packageName: String,
                                  outputDir: File) {
        when (screen.template) {
            "list" -> generateListScreen(screen, packageName, outputDir)
        }
    }

    private fun generateListScreen(screen: Screen,
                                   packageName: String,
                                   outputDir: File) {

        this.screen = screen
        this.packageName = packageName
        this.outputDir = outputDir

        baseType = ClassName.bestGuess(screen.baseType.canonicalName)
        baseListType = ParameterizedTypeName.get(LIST_TYPE, baseType)
        baseMutableListType = ParameterizedTypeName.get(MUTABLELIST_TYPE, baseType)

        contractType = ClassName.bestGuess("$packageName.${TripleA.GENERATED_CLASS_PREFIX}${screen.baseType.simpleName}$SUFFIX_CONTRACT")
        viewType = contractType.nestedClass(SUFFIX_VIEW)
        presenterType = contractType.nestedClass(SUFFIX_PRESENTER)
        dataSourceType = ParameterizedTypeName.get(DATASOURCE_TYPE, baseListType)

        generateMVPListContract()
        generateMVPListPresenter()
        generateMVPListFragment()
        generateMVPListAdapter()
        generateMVPListViewHolder()
        generateMVPListActivity()
    }


    private fun generateMVPListActivity() {
        val className = "${TripleA.GENERATED_CLASS_PREFIX}${screen.baseType.simpleName}$SUFFIX_ACTIVITY"
        val fragmentClassName = "${TripleA.GENERATED_CLASS_PREFIX}${screen.baseType.simpleName}$SUFFIX_FRAGMENT"

        val fileBuilder = FileSpec.builder(packageName, className)
                .addComment(TripleA.HEADER_DISCLAIMER)

        val parentType = ParameterizedTypeName.get(ClassName.bestGuess(TripleA.Core.Front.MVP.BaseActivity.CANONICAL_NAME),
                presenterType, ClassName.bestGuess("$packageName.$fragmentClassName")
        )

        val activity = TypeSpec.classBuilder(className)
                .superclass(parentType)
                .addModifiers(KModifier.ABSTRACT)

        // handleIntent
        activity.addFunction(
                FunSpec.builder("handleIntent")
                        .addModifiers(KModifier.OVERRIDE, KModifier.OPEN)
                        .addParameter("intent", INTENT_TYPE)
                        .build()
        )

        // getPresenterKey()
        activity.addFunction(
                FunSpec.builder("getPresenterKey")
                        .addModifiers(KModifier.OVERRIDE, KModifier.OPEN)
                        .returns(ClassName.bestGuess("kotlin.String"))
                        .addStatement("return \"${screen.key}\"")
                        .build()
        )


        fileBuilder.addType(activity.build())
        fileBuilder.build().writeTo(outputDir)
    }

    private fun generateMVPListContract() {

        val className = "${TripleA.GENERATED_CLASS_PREFIX}${screen.baseType.simpleName}$SUFFIX_CONTRACT"

        val fileBuilder = FileSpec.builder(packageName, className)
                .addComment(TripleA.HEADER_DISCLAIMER)


        val presenterBuilder = TypeSpec.interfaceBuilder(SUFFIX_PRESENTER)
                .addSuperinterface(ParameterizedTypeName.get(LISTCONTRACT_PRESENTER_TYPE, baseType))
        if (screen.params["refreshable"]?.toBoolean() == true) {
            presenterBuilder.addFunction(FunSpec.builder("onRefresh").addModifiers(KModifier.ABSTRACT).build())
        }

        val viewBuilder = TypeSpec.interfaceBuilder(SUFFIX_VIEW)
                .addSuperinterface(ParameterizedTypeName.get(LISTCONTRACT_VIEW_TYPE, baseType))

        val contractBuilder = TypeSpec.interfaceBuilder(className)
                .addType(presenterBuilder.build())
                .addType(viewBuilder.build())


        fileBuilder.addType(contractBuilder.build())
        fileBuilder.build().writeTo(outputDir)
    }

    private fun generateMVPListPresenter() {

        val className = "${TripleA.GENERATED_CLASS_PREFIX}${screen.baseType.simpleName}$SUFFIX_PRESENTER"

        val fileBuilder = FileSpec.builder(packageName, className)
                .addComment(TripleA.HEADER_DISCLAIMER)

        val typeBuilder = TypeSpec.classBuilder(className)
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
                        .addParameter("v", CONTRACT_VIEW_TYPE)
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

        // onEntitySelected
        typeBuilder.addFunction(
                FunSpec.builder("onEntitySelected")
                        .addParameter("entity", baseType)
                        .addModifiers(KModifier.OVERRIDE, KModifier.OPEN)
                        .addStatement("view?.navigateToEntity(entity)")
                        .build()
        )

        if (screen.params["refreshable"]?.toBoolean() == true) {
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
                        .addParameter("throwable", ClassName.bestGuess("kotlin.Throwable"))
                        .addModifiers(KModifier.PROTECTED, KModifier.OPEN)
                        .addStatement("view?.showError(throwable)")
                        .build()
        )

        fileBuilder.addType(typeBuilder.build())
        fileBuilder.build().writeTo(outputDir)
    }


    private fun generateMVPListFragment() {

        val className = "${TripleA.GENERATED_CLASS_PREFIX}${screen.baseType.simpleName}$SUFFIX_FRAGMENT"
        val adapterClassName = "${TripleA.GENERATED_CLASS_PREFIX}${screen.baseType.simpleName}$SUFFIX_ADAPTER"

        val fileBuilder = FileSpec.builder(packageName, className)
                .addComment(TripleA.HEADER_DISCLAIMER)

        val typeBuilder = TypeSpec.classBuilder(className)
                .superclass(FRAGMENT_TYPE)
                .addSuperinterface(viewType)
                .addModifiers(KModifier.ABSTRACT)
                .addProperty(PropertySpec.varBuilder("presenter", presenterType, KModifier.PROTECTED, KModifier.LATEINIT).build())
                .addProperty(PropertySpec.builder("adapter", ClassName.bestGuess("$packageName.$adapterClassName"), KModifier.PROTECTED, KModifier.ABSTRACT).build())
                .addProperty(PropertySpec.varBuilder("contentView", ANDROID_VIEW_TYPE.asNullable(), KModifier.PROTECTED).initializer("null").build())
                .addProperty(PropertySpec.varBuilder("recyclerView", RECYCLERVIEW_TYPE.asNullable(), KModifier.PROTECTED).initializer("null").build())
                .addProperty(PropertySpec.builder("layoutId", INT, KModifier.PROTECTED, KModifier.OPEN)
                        .initializer("%T.layout.fragment_list", R_TYPE)
                        .addAnnotation(LAYOUTRES_TYPE)
                        .build())

        if (screen.params["refreshable"]?.toBoolean() == true) {
            typeBuilder.addProperty(PropertySpec.varBuilder("swipeRefreshLayout", SWIPEREFRESHLAYOUT_TYPE.asNullable(), KModifier.PROTECTED).initializer("null").build())
        }
        if (screen.params["empty_state"]?.toBoolean() == true) {
            typeBuilder.addProperty(PropertySpec.varBuilder("emptyView", ANDROID_VIEW_TYPE.asNullable(), KModifier.PROTECTED).initializer("null").build())
        }

        // setPresenter
        val setPresenterFun = FunSpec.builder("setPresenter")
                .addParameter("p", CONTRACT_PRESENTER_TYPE)
                .addModifiers(KModifier.OVERRIDE)
                .addStatement("require(p is %T)", presenterType)
                .addStatement("presenter = p as %T", presenterType)
        typeBuilder.addFunction(setPresenterFun.build())

        // onCreate
        val onCreateViewFun = FunSpec.builder("onCreateView")
                .addModifiers(KModifier.OVERRIDE)
                .addParameter("layoutInflater", LAYOUTINFLATER_TYPE)
                .addParameter("container", ClassName.bestGuess("android.view.ViewGroup").asNullable())
                .addParameter("savedInstanceState", ClassName.bestGuess("android.os.Bundle").asNullable())
                .returns(ANDROID_VIEW_TYPE.asNullable())
                .addStatement("val rootView = layoutInflater.inflate(layoutId, container, false)")
                .addStatement("contentView = rootView", R_TYPE)

        if (screen.params["refreshable"]?.toBoolean() == true) {
            onCreateViewFun.addStatement("swipeRefreshLayout = rootView.findViewById(%T.id.refresh)", R_TYPE)
        }
        if (screen.params["empty_state"]?.toBoolean() == true) {
            onCreateViewFun.addStatement("emptyView = rootView.findViewById(%T.id.empty)", R_TYPE)
        }
        onCreateViewFun.addStatement("recyclerView = rootView.findViewById(%T.id.recycler)", R_TYPE)
                .addStatement("recyclerView?.adapter = adapter")
                .addStatement("recyclerView?.layoutManager = %T(context)", LINEARLAYOUTMGR_TYPE)
        onCreateViewFun.addStatement("return rootView")
        typeBuilder.addFunction(onCreateViewFun.build())

        // showEntities
        val showEntitiesFun = FunSpec.builder("showEntities")
                .addParameter("entities", baseListType)
                .addModifiers(KModifier.OVERRIDE, KModifier.OPEN)
                .addStatement("adapter.update(entities)")
        if (screen.params["refreshable"]?.toBoolean() == true) {
            showEntitiesFun.addStatement("swipeRefreshLayout?.isRefreshing = false")
        }
        typeBuilder.addFunction(showEntitiesFun.build())

        // showLoading
        val showLoadingFun = FunSpec.builder("showLoading")
                .addModifiers(KModifier.OVERRIDE, KModifier.OPEN)
        if (screen.params["refreshable"]?.toBoolean() == true) {
            showLoadingFun.addStatement("swipeRefreshLayout?.isRefreshing = true")
        }
        typeBuilder.addFunction(showLoadingFun.build())


        // ShowError
        val showErrorFun = FunSpec.builder("showError")
                .addParameter("throwable", ClassName.bestGuess("kotlin.Throwable"))
                .addModifiers(KModifier.OVERRIDE, KModifier.OPEN)
                .addStatement("val messageId = getErrorMessage(throwable)")
        if (screen.params["refreshable"]?.toBoolean() == true) {
            showErrorFun.addStatement("swipeRefreshLayout?.isRefreshing = false")
        }
        showErrorFun.beginControlFlow("contentView?.let")
                .beginControlFlow("if (messageId == 0)")
                .addStatement("%T.make(it, \"An unknown error occurred ‽\", %T.LENGTH_LONG).show();", SNACKBAR_TYPE, SNACKBAR_TYPE)
                .nextControlFlow("else")
                .addStatement("%T.make(it, messageId, %T.LENGTH_LONG).show();", SNACKBAR_TYPE, SNACKBAR_TYPE)
                .endControlFlow()
                .endControlFlow()
        typeBuilder.addFunction(showErrorFun.build())

        typeBuilder.addFunction(
                FunSpec.builder("getErrorMessage")
                        .addParameter("throwable", ClassName.bestGuess("kotlin.Throwable"))
                        .returns(INT)
                        .addAnnotation(STRINGRES_TYPE)
                        .addModifiers(KModifier.PROTECTED, KModifier.OPEN)
                        .addStatement("return 0")
                        .build()
        )

        fileBuilder.addType(typeBuilder.build())
        fileBuilder.build().writeTo(outputDir)
    }

    private fun generateMVPListAdapter() {
        val className = "${TripleA.GENERATED_CLASS_PREFIX}${screen.baseType.simpleName}$SUFFIX_ADAPTER"
        val vhClassName = "${TripleA.GENERATED_CLASS_PREFIX}${screen.baseType.simpleName}$SUFFIX_VIEWHOLDER"

        val vhType = ClassName.bestGuess("$packageName.$vhClassName")
        val rvType = ParameterizedTypeName.get(RECYCLERVIEW_TYPE.nestedClass("Adapter"), vhType)

        val fileBuilder = FileSpec.builder(packageName, className)
                .addComment(TripleA.HEADER_DISCLAIMER)

        val typeBuilder = TypeSpec.classBuilder(className)
                .superclass(rvType)
                .addModifiers(KModifier.ABSTRACT)
                .addProperty(PropertySpec.builder("data", baseMutableListType, KModifier.PROTECTED).initializer("mutableListOf()").build())

        // getItemViewType
        if (screen.params["single_layout"]?.toBoolean() == true) {
            typeBuilder.addProperty(PropertySpec.builder("layoutId", INT, KModifier.PROTECTED, KModifier.ABSTRACT).build())
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
            if (screen.params["databinding"]?.toBoolean() != true) {
                typeBuilder.addFunction(
                        FunSpec.builder("getLayoutId")
                                .addModifiers(KModifier.ABSTRACT)
                                .addAnnotation(LAYOUTRES_TYPE)
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
                .addParameter("parent", VIEWGROUP_TYPE)
                .addParameter("viewType", INT)
                .returns(vhType)
        if (screen.params["databinding"]?.toBoolean() == true) {
            onCreateVHFun
                    .addStatement("val layoutInflater = %T.from(parent.context)", LAYOUTINFLATER_TYPE)
                    .addStatement("val binding = inflateDataBinding(layoutInflater, parent, viewType)")
                    .addStatement("return instantiateViewHolder(binding, viewType)", vhType)
        } else {
            if (screen.params["single_layout"]?.toBoolean() != true) {
                onCreateVHFun.addStatement("val layoutId = getLayoutId(viewType)")
            }
            onCreateVHFun.addStatement("val view = %T.from(parent.context).inflate(layoutId, parent, false)", LAYOUTINFLATER_TYPE)
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
                        .addStatement(" val diffResult = %T.calculateDiff(diffCallback)", DIFFUTIL_TYPE)
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
                        .returns(DIFFUTIL_CALLBACK_TYPE.asNullable())
                        .addStatement("return null")
                        .build())


        if (screen.params["databinding"]?.toBoolean() == true) {
            typeBuilder.addFunction(
                    FunSpec.builder("inflateDataBinding")
                            .addModifiers(KModifier.ABSTRACT)
                            .addParameter("layoutInflater", LAYOUTINFLATER_TYPE)
                            .addParameter("parent", VIEWGROUP_TYPE)
                            .addParameter("viewType", INT)
                            .returns(VIEWDATABINDING_TYPE)
                            .build()
            )
            typeBuilder.addFunction(
                    FunSpec.builder("instantiateViewHolder")
                            .addModifiers(KModifier.ABSTRACT)
                            .addParameter("binding", VIEWDATABINDING_TYPE)
                            .addParameter("viewType", INT)
                            .returns(vhType)
                            .build()
            )
        } else {
            typeBuilder.addFunction(
                    FunSpec.builder("instantiateViewHolder")
                            .addModifiers(KModifier.ABSTRACT)
                            .addParameter("view", ANDROID_VIEW_TYPE)
                            .addParameter("viewType", INT)
                            .returns(vhType)
                            .build()
            )
        }

        fileBuilder.addType(typeBuilder.build())
        fileBuilder.build().writeTo(outputDir)
    }

    private fun generateMVPListViewHolder() {
        val className = "${TripleA.GENERATED_CLASS_PREFIX}${screen.baseType.simpleName}$SUFFIX_VIEWHOLDER"

        val fileBuilder = FileSpec.builder(packageName, className)
                .addComment(TripleA.HEADER_DISCLAIMER)

        val typeBuilder = TypeSpec.classBuilder(className)
                .superclass(RECYCLERVIEW_TYPE.nestedClass("ViewHolder"))
                .addModifiers(KModifier.ABSTRACT)
                .primaryConstructor(FunSpec.constructorBuilder()
                        .addParameter("itemView", ANDROID_VIEW_TYPE)
                        .build())
                .addSuperclassConstructorParameter("itemView")
                .addProperty(PropertySpec.varBuilder("item", baseType).addModifiers(KModifier.LATEINIT, KModifier.PROTECTED).build())


        // bindItem
        typeBuilder.addFunction(
                FunSpec.builder("bindItem")
                        .addParameter("item", baseType)
                        .addStatement("this.item = item")
                        .addStatement("onBindItem(item)")
                        .build()
        )
        typeBuilder.addFunction(
                FunSpec.builder("onBindItem")
                        .addModifiers(KModifier.ABSTRACT)
                        .addParameter("item", baseType)
                        .build()
        )

        fileBuilder.addType(typeBuilder.build())
        fileBuilder.build().writeTo(outputDir)
    }
}