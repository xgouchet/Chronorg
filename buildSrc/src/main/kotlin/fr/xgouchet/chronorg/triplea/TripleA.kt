package fr.xgouchet.chronorg.triplea

object TripleA {

    const val GENERATED_CLASS_PREFIX = "TA"
    const val PACKAGE_NAME = "fr.xgouchet.triplea"


    object Core {
        const val PACKAGE_NAME = "${TripleA.PACKAGE_NAME}.core"

        const val R = "$PACKAGE_NAME.R"

        object Source {
            const val PACKAGE_NAME = "${TripleA.Core.PACKAGE_NAME}.source"

            object DataSource {
                const val CLASS_NAME = "DataSource"
                const val CANONICAL_NAME = "${TripleA.Core.Source.PACKAGE_NAME}.$CLASS_NAME"
            }
        }

        object Front {
            const val PACKAGE_NAME = "${TripleA.Core.PACKAGE_NAME}.front"

            object MVVM {
                const val PACKAGE_NAME = "${TripleA.Core.Front.PACKAGE_NAME}.mvp"

                object BaseActivity {
                    const val CLASS_NAME = "BaseActivity"
                    const val CANONICAL_NAME = "${TripleA.Core.Front.MVP.PACKAGE_NAME}.$CLASS_NAME"
                }
            }

            object MVP {
                const val PACKAGE_NAME = "${TripleA.Core.Front.PACKAGE_NAME}.mvp"

                object BaseActivity {
                    const val CLASS_NAME = "BaseActivity"
                    const val CANONICAL_NAME = "${TripleA.Core.Front.MVP.PACKAGE_NAME}.$CLASS_NAME"
                }

                object BaseViewPagerActivity {
                    const val CLASS_NAME = "BaseViewPagerActivity"
                    const val CANONICAL_NAME = "${TripleA.Core.Front.MVP.PACKAGE_NAME}.$CLASS_NAME"
                }

                object BaseViewPagerAdapter {
                    const val CLASS_NAME = "BaseViewPagerAdapter"
                    const val CANONICAL_NAME = "${TripleA.Core.Front.MVP.PACKAGE_NAME}.$CLASS_NAME"
                }


                object BaseContract {
                    const val CLASS_NAME = "BaseContract"
                    const val CANONICAL_NAME = "${TripleA.Core.Front.MVP.PACKAGE_NAME}.$CLASS_NAME"

                    object Presenter {
                        const val CLASS_NAME = "Presenter"
                        const val CANONICAL_NAME = "${TripleA.Core.Front.MVP.BaseContract.CANONICAL_NAME}.$CLASS_NAME"
                    }

                    object View {
                        const val CLASS_NAME = "View"
                        const val CANONICAL_NAME = "${TripleA.Core.Front.MVP.BaseContract.CANONICAL_NAME}.$CLASS_NAME"
                    }
                }

                object BaseListContract {
                    const val CLASS_NAME = "BaseListContract"
                    const val CANONICAL_NAME = "${TripleA.Core.Front.MVP.PACKAGE_NAME}.$CLASS_NAME"

                    object Presenter {
                        const val CLASS_NAME = "Presenter"
                        const val CANONICAL_NAME = "${TripleA.Core.Front.MVP.BaseListContract.CANONICAL_NAME}.$CLASS_NAME"
                    }

                    object View {
                        const val CLASS_NAME = "View"
                        const val CANONICAL_NAME = "${TripleA.Core.Front.MVP.BaseListContract.CANONICAL_NAME}.$CLASS_NAME"
                    }
                }
            }
        }
    }
}