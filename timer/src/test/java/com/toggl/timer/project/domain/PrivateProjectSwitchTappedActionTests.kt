package com.toggl.timer.project.domain

import com.toggl.architecture.extensions.noEffect
import com.toggl.timer.common.CoroutineTest
import com.toggl.models.domain.EditableProject
import com.toggl.timer.common.testReduce
import io.kotlintest.shouldBe
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
@DisplayName("The PrivateProjectSwitchTapped action")
internal class PrivateProjectSwitchTappedActionTests : CoroutineTest() {
    val reducer = ProjectReducer(mockk(), dispatcherProvider)

    @Test
    fun `should toggle the private flag and return no effect`() = runBlockingTest {
        val initialState = ProjectState(EditableProject.empty(1).copy(isPrivate = true), emptyMap())

        reducer.testReduce(
            initialState,
            ProjectAction.PrivateProjectSwitchTapped
        ) { state, effects ->
            state.editableProject!!.isPrivate shouldBe false
            effects shouldBe noEffect()
        }
    }
}