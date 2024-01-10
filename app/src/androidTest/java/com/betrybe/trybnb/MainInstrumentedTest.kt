package com.betrybe.trybnb

import android.content.Context
import android.widget.Button
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.betrybe.trybnb.common.ApiIdlingResource
import com.betrybe.trybnb.ui.views.activities.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

private fun getId(id: String): Int {
    val targetContext: Context = InstrumentationRegistry.getInstrumentation().targetContext
    val packageName: String = targetContext.packageName
    return targetContext.resources.getIdentifier(id, "id", packageName)
}

@RunWith(AndroidJUnit4::class)
class MainInstrumentedTest {

    @get:Rule
    val activityRuleMain = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(ApiIdlingResource.getIdlingResource())
    }

    @After
    fun teardown() {
        IdlingRegistry.getInstance().unregister(ApiIdlingResource.getIdlingResource())
    }

    @Test
    fun test_req_1_configure_a_estrutura_inicial_da_tela_incluindo_a_barra_superior_com_o_logotipo() {
        onView(withId(getId("main_container")))
            .check(matches(isDisplayed()))
            .check(matches(isAssignableFrom(LinearLayout::class.java)))

        onView(withId(getId("navigation_bar_container")))
            .check(matches(isDisplayed()))
            .check(matches(isAssignableFrom(ConstraintLayout::class.java)))
            .check(matches(withParent(withId(getId("main_container")))))

        onView(withId(getId("logo_main_activity")))
            .check(matches(isDisplayed()))
            .check(matches(isAssignableFrom(ImageView::class.java)))
            .check(matches(withParent(withId(getId("navigation_bar_container")))))
    }

    @Test
    fun test_req_2_configure_a_estrutura_da_tela_inicial_adicionando_uma_barra_de_navegacao_na_parte_inferior_e_um_fragment_container() {
        onView(withId(getId("main_fragment_container")))
            .check(matches(isDisplayed()))
            .check(matches(isAssignableFrom(FragmentContainerView::class.java)))
            .check(matches(withParent(withId(getId("main_container")))))

        onView(withId(getId("navigation_bottom_view")))
            .check(matches(isDisplayed()))
            .check(matches(isAssignableFrom(BottomNavigationView::class.java)))
            .check(matches(withParent(withId(getId("main_container")))))
    }

    @Test
    fun test_req_3_Implemente_a_navegacao_do_bottom_navigation_de_forma_que_ao_clicar_em_cada_item_do_menu_seja_chamado_o_respectivo_fragmento() {
        onView(withId(getId("reservation_menu_item"))).perform(click())

        onView(withId(getId("reservation_frame_layout")))

        onView(withId(getId("create_reservation_menu_item"))).perform(click())

        onView(withId(getId("create_reservation_scroll_view")))

        onView(withId(getId("profile_menu_tem"))).perform(click())

        onView(withId(getId("profile_scroll_view")))
    }

    @Test
    fun test_req_4_crie_a_estrutura_do_fragmento_perfil() {
        onView(withId(getId("profile_menu_tem"))).perform(click())

        onView(withId(getId("profile_scroll_view")))

        onView(withId(getId("profile_container")))
            .check(matches(isDisplayed()))
            .check(matches(isAssignableFrom(LinearLayout::class.java)))
            .check(matches(withParent(withId(getId("profile_scroll_view")))))

        onView(withId(getId("person_image_profile")))
            .check(matches(isDisplayed()))
            .check(matches(isAssignableFrom(ImageView::class.java)))
            .check(matches(withParent(withId(getId("profile_container")))))

        onView(withId(getId("login_input_profile")))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
            .check(matches(isAssignableFrom(TextInputLayout::class.java)))
            .check(matches(withParent(withId(getId("profile_container")))))

        onView(withId(getId("password_input_profile")))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
            .check(matches(isAssignableFrom(TextInputLayout::class.java)))
            .check(matches(withParent(withId(getId("profile_container")))))

        onView(withId(getId("login_button_profile")))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
            .check(matches(isAssignableFrom(Button::class.java)))
            .check(matches(withParent(withId(getId("profile_container")))))
    }

    @Test
    fun test_req_5_implemente_a_validacao_do_campo_login_e_password() {
        onView(withId(getId("profile_menu_tem"))).perform(click())

        onView(withId(getId("login_button_profile")))
            .perform(scrollTo())
            .perform(click())

        onView(withText("O campo Login é obrigatório"))
            .perform(scrollTo())
            .check(matches(isDisplayed()))

        onView(withText("O campo Password é obrigatório"))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_req_6_realizar_requisicao_ao_endpoint_post_auth_via_retrofit() {
        onView(withId(getId("profile_menu_tem"))).perform(click())

        onView(withHint("Login"))
            .perform(scrollTo())
            .perform(typeText("admin"))

        closeSoftKeyboard()

        onView(withHint("Password"))
            .perform(scrollTo())
            .perform(typeText("password123"))

        closeSoftKeyboard()

        onView(withId(getId("login_button_profile")))
            .perform(scrollTo())
            .perform(click())

        onView(withText("Login feito com sucesso!"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_req_7_crie_a_estrutura_do_layout_do_fragmento_reservas() {
        onView(withId(getId("reservation_menu_item"))).perform(click())

        onView(withId(getId("reservation_frame_layout")))
            .check(matches(isDisplayed()))
            .check(matches(isAssignableFrom(FrameLayout::class.java)))

        onView(withId(getId("reservation_recycler_view")))
            .check(matches(isDisplayed()))
            .check(matches(isAssignableFrom(RecyclerView::class.java)))
            .check(matches(withParent(withId(getId("reservation_frame_layout")))))
    }

    @Test
    fun test_req_8_desenvolva_o_layout_do_item_e_popule_a_lista_com_as_informacoes_provenientes_da_api() {
        onView(withId(getId("reservation_menu_item"))).perform(click())

        onView(withId(getId("reservation_recycler_view")))
            .check(
                matches(
                    hasDescendant(
                        allOf(
                            withId(getId("card_view_item_reservation")),
                            hasDescendant(withId(getId("depositpaid_item_reservation"))),
                            hasDescendant(withId(getId("name_item_reservation"))),
                            hasDescendant(withId(getId("checkin_item_reservation"))),
                            hasDescendant(withId(getId("checkout_item_reservation"))),
                            hasDescendant(withId(getId("additional_needs_item_reservation"))),
                            hasDescendant(withId(getId("total_price_item_reservation")))
                        )
                    )
                )
            )
    }

    @Test
    fun test_req_9_desenvolva_o_layout_da_tela_criar_uma_reserva() {
        onView(withId(getId("create_reservation_menu_item"))).perform(click())

        onView(withId(getId("create_reservation_scroll_view")))
            .check(matches(isDisplayed()))
            .check(matches(isAssignableFrom(ScrollView::class.java)))

        onView(withId(getId("title_create_reservation")))
            .check(matches(isDisplayed()))
            .check(matches(isAssignableFrom(MaterialTextView::class.java)))

        onView(withId(getId("first_name_create_reservation")))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
            .check(matches(isAssignableFrom(TextInputLayout::class.java)))

        onView(withId(getId("last_name_create_reservation")))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
            .check(matches(isAssignableFrom(TextInputLayout::class.java)))

        onView(withId(getId("checkin_create_reservation")))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
            .check(matches(isAssignableFrom(TextInputLayout::class.java)))

        onView(withId(getId("checkout_create_reservation")))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
            .check(matches(isAssignableFrom(TextInputLayout::class.java)))

        onView(withId(getId("additional_needs_create_reservation")))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
            .check(matches(isAssignableFrom(TextInputLayout::class.java)))

        onView(withId(getId("total_price_create_reservation")))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
            .check(matches(isAssignableFrom(TextInputLayout::class.java)))

        onView(withId(getId("depositpaid_create_reservation")))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
            .check(matches(isAssignableFrom(CheckBox::class.java)))

        onView(withId(getId("create_reservation_button")))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
            .check(matches(isAssignableFrom(MaterialButton::class.java)))
    }

    @Test
    fun test_req_10_testar_validacoes_da_tela_criar_reserva() {
        onView(withId(getId("create_reservation_menu_item"))).perform(click())

        onView(withId(getId("create_reservation_button")))
            .perform(scrollTo())
            .perform(click())

        onView(withText("O campo Nome é obrigatório"))
            .perform(scrollTo())
            .check(matches(isDisplayed()))

        onView(withText("O campo Sobrenome é obrigatório"))
            .perform(scrollTo())
            .check(matches(isDisplayed()))

        onView(withText("O campo Checkin é obrigatório"))
            .perform(scrollTo())
            .check(matches(isDisplayed()))

        onView(withText("O campo Checkout é obrigatório"))
            .perform(scrollTo())
            .check(matches(isDisplayed()))

        onView(withText("O campo Necessidades Adicionais é obrigatório"))
            .perform(scrollTo())
            .check(matches(isDisplayed()))

        onView(withText("O campo Preço Total é obrigatório"))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_req_11_realizar_requisicao_ao_endpoint_post_booking_usando_retrofit() {
        onView(withId(getId("create_reservation_menu_item"))).perform(click())

        onView(withHint("Nome do hóspede"))
            .perform(scrollTo())
            .perform(typeText("Italo"))

        closeSoftKeyboard()

        onView(withHint("Sobrenome do hóspede"))
            .perform(scrollTo())
            .perform(typeText("Moura"))

        closeSoftKeyboard()

        onView(withHint("Checkin"))
            .perform(scrollTo())
            .perform(typeText("10/11/2023"))

        closeSoftKeyboard()

        onView(withHint("Checkout"))
            .perform(scrollTo())
            .perform(typeText("19/11/2023"))

        closeSoftKeyboard()

        onView(withHint("Necessidades adicionais"))
            .perform(scrollTo())
            .perform(typeText("Central de ar"))

        closeSoftKeyboard()

        onView(withHint("Preço total"))
            .perform(scrollTo())
            .perform(typeText("223"))

        closeSoftKeyboard()

        onView(withId(getId("create_reservation_button")))
            .perform(scrollTo())
            .perform(click())

        onView(withText("Reserva feita com sucesso!"))
            .check(matches(isDisplayed()))
    }
}
