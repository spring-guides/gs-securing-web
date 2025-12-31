package com.example.securingweb

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class SecuringWebApplicationTests(@Autowired private val mockMvc: MockMvc) {

    @Test
    fun loginWithValidUserThenAuthenticated() {
        val login = SecurityMockMvcRequestBuilders.formLogin()
            .user("user")
            .password("password")

        mockMvc.perform(login)
            .andExpect(SecurityMockMvcResultMatchers.authenticated().withUsername("user"))
    }

    @Test
    fun loginWithInvalidUserThenUnauthenticated() {
        val login = SecurityMockMvcRequestBuilders.formLogin()
            .user("invalid")
            .password("invalidpassword")

        mockMvc.perform(login)
            .andExpect(SecurityMockMvcResultMatchers.unauthenticated())
    }

    @Test
    fun accessUnsecuredResourceThenOk() {
        mockMvc.perform(get("/"))
            .andExpect(status().isOk())
    }

    @Test
    fun accessSecuredResourceUnauthenticatedThenRedirectsToLogin() {
        mockMvc.perform(get("/hello"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/login"))
    }

    @Test
    @WithMockUser
    fun accessSecuredResourceAuthenticatedThenOk() {
        val mvcResult = mockMvc.perform(get("/hello"))
            .andExpect(status().isOk())
            .andReturn()

        Assertions.assertThat(mvcResult.response.contentAsString).contains("Hello user!")
    }
}