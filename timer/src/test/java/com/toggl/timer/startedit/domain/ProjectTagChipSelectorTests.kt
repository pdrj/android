package com.toggl.timer.startedit.domain

import com.toggl.models.domain.Project
import com.toggl.models.domain.Tag
import com.toggl.timer.common.createTimeEntry
import com.toggl.models.domain.EditableTimeEntry
import com.toggl.timer.project.domain.createProject
import com.toggl.timer.startedit.ui.chips.ChipViewModel
import io.kotlintest.matchers.boolean.shouldBeTrue
import io.kotlintest.matchers.collections.shouldBeEmpty
import io.kotlintest.matchers.types.shouldNotBeNull
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@kotlinx.coroutines.ExperimentalCoroutinesApi
@DisplayName("The ProjectTagChipSelector returns")
internal class ProjectTagChipSelectorTests {

    private val tagLabel = "Add Tags"
    private val projectLabel = "Add a Project"

    @Test
    fun `an add project chip when there are no projects`() {

        val timeEntry = createTimeEntry(1, projectId = null)
        val projects = mapOf<Long, Project>()
        val tags = mapOf<Long, Tag>()
        val editableTimeEntry = EditableTimeEntry.fromSingle(timeEntry)

        val chips = projectTagChipSelector(projectLabel, tagLabel, editableTimeEntry, projects, tags)

        chips.filterIsInstance<ChipViewModel.AddProject>().singleOrNull { it.text == projectLabel }.shouldNotBeNull()
    }

    @Test
    fun `a project chip when there is a project`() {

        val project = createProject(1)
        val timeEntry = createTimeEntry(2, projectId = project.id)
        val projects = mapOf(project.id to project)
        val tags = mapOf<Long, Tag>()
        val editableTimeEntry = EditableTimeEntry.fromSingle(timeEntry)

        val chips = projectTagChipSelector(projectLabel, tagLabel, editableTimeEntry, projects, tags)

        chips.filterIsInstance<ChipViewModel.Project>().singleOrNull { it.project == project }.shouldNotBeNull()
    }

    @Test
    fun `no add project chip when there is a project`() {

        val project = createProject(1)
        val timeEntry = createTimeEntry(2, projectId = project.id)
        val projects = mapOf(project.id to project)
        val tags = mapOf<Long, Tag>()
        val editableTimeEntry = EditableTimeEntry.fromSingle(timeEntry)

        val chips = projectTagChipSelector(projectLabel, tagLabel, editableTimeEntry, projects, tags)

        chips.filterIsInstance<ChipViewModel.AddProject>().shouldBeEmpty()
    }

    @Test
    fun `a tag chip for each tag in the editable time entry`() {

        val tags = (1L..10L).map { Tag(it, "Tag $it", 1) }
        val timeEntry = createTimeEntry(2, tags = tags.map { it.id })
        val projects = mapOf<Long, Project>()
        val tagDictionary = tags.associateBy { it.id }
        val editableTimeEntry = EditableTimeEntry.fromSingle(timeEntry)

        val chips = projectTagChipSelector(projectLabel, tagLabel, editableTimeEntry, projects, tagDictionary)

        chips.filterIsInstance<ChipViewModel.Tag>().all { tags.contains(it.tag) }.shouldBeTrue()
    }

    @Test
    fun `an add tag chip when there are tags`() {

        val tags = (1L..10L).map { Tag(it, "Tag $it", 1) }
        val timeEntry = createTimeEntry(2, tags = tags.map { it.id })
        val projects = mapOf<Long, Project>()
        val tagDictionary = tags.associateBy { it.id }
        val editableTimeEntry = EditableTimeEntry.fromSingle(timeEntry)

        val chips = projectTagChipSelector(projectLabel, tagLabel, editableTimeEntry, projects, tagDictionary)

        chips.any { it is ChipViewModel.AddTag && it.text == tagLabel }.shouldBeTrue()
    }

    @Test
    fun `an add tag chip when there are no tags`() {

        val timeEntry = createTimeEntry(2, tags = emptyList())
        val projects = mapOf<Long, Project>()
        val tags = mapOf<Long, Tag>()
        val editableTimeEntry = EditableTimeEntry.fromSingle(timeEntry)

        val chips = projectTagChipSelector(projectLabel, tagLabel, editableTimeEntry, projects, tags)
        chips.any { it is ChipViewModel.AddTag && it.text == tagLabel }.shouldBeTrue()
    }
}
