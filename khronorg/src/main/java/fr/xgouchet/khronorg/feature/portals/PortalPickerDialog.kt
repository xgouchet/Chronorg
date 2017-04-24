package fr.xgouchet.khronorg.ui.dialog

import fr.xgouchet.khronorg.commons.formatters.DefaultIntervalFormatter
import fr.xgouchet.khronorg.feature.portals.Portal
import fr.xgouchet.khronorg.feature.portals.PortalAdapter
import fr.xgouchet.khronorg.ui.adapters.BaseAdapter
import fr.xgouchet.khronorg.ui.fragments.ListFragmentDialog

/**
 * @author Xavier F. Gouchet
 */
class PortalPickerDialog : ListFragmentDialog<Portal>(false) {


    override val adapter: BaseAdapter<Portal> = PortalAdapter(DefaultIntervalFormatter, this)


}