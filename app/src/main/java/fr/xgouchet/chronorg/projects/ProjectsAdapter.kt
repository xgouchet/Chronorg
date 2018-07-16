package fr.xgouchet.chronorg.projects

import android.databinding.ViewDataBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import fr.xgouchet.chronorg.databinding.ItemProjectBindingBinding
import fr.xgouchet.chronorg.models.Project

class ProjectsAdapter(listener: ((Project) -> Unit)?)
    : TAProjectsAdapter(listener) {
    override fun inflateDataBinding(layoutInflater: LayoutInflater, parent: ViewGroup, viewType: Int): ViewDataBinding? {
        return ItemProjectBindingBinding.inflate(layoutInflater, parent, false)
    }
}