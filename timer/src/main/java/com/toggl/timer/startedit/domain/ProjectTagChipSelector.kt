package com.toggl.timer.startedit.domain

import com.toggl.architecture.core.Selector
import com.toggl.timer.exceptions.ProjectDoesNotExistException
import com.toggl.timer.exceptions.TagDoesNotExistException
import com.toggl.timer.startedit.ui.chips.ChipViewModel
import javax.inject.Singleton

@Singleton
class ProjectTagChipSelector(
    private val addProjectLabel: String,
    private val addTagLabel: String
) : Selector<StartEditState, List<ChipViewModel>> {

    override suspend fun select(state: StartEditState): List<ChipViewModel> {

        val editableTimeEntry = state.editableTimeEntry ?: return emptyList()

        return sequence {
            if (editableTimeEntry.projectId != null) {
                val project = state.projects[editableTimeEntry.projectId] ?: throw ProjectDoesNotExistException()
                yield(ChipViewModel.Project(project))
            } else {
                yield(ChipViewModel.AddProject(addProjectLabel))
            }

            for (tagId in editableTimeEntry.tagIds) {
                val tag = state.tags[tagId] ?: throw TagDoesNotExistException()
                yield(ChipViewModel.Tag(tag))
            }

            yield(ChipViewModel.AddTag(addTagLabel))
        }.toList()
    }
}