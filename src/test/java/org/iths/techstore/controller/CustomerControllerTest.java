package org.iths.techstore.controller;

import org.iths.techstore.Model.Customer;
import org.iths.techstore.Repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("Create customer via controller and verify persistence")
    void testCreateCustomer() throws Exception {

        Customer customer = new Customer();
        customer.setName("Lalisa Manoban");
        customer.setTelefonNumber("0701234567");
        customer.setEmail("lalisa@manobal.se");

        mockMvc.perform(post("/customers")
                        .flashAttr("customer", customer))
                .andExpect(status().isFound());

        assertThat(customerRepository.count()).isEqualTo(1);

        Customer saved = customerRepository.findAll().get(0);
        assertThat(saved.getName()).isEqualTo("Lalisa Manoban");
        assertThat(saved.getEmail()).isEqualTo("lalisa@manobal.se");
    }

    @Test
    @DisplayName("Fetch single customer and verify it exists")
    void testGetCustomer() throws Exception {

        Customer customer = new Customer();
        customer.setName("Bianca Ingrosso");
        customer.setTelefonNumber("0709876543");
        customer.setEmail("bianca@ingrosso.se");

        Customer saved = customerRepository.save(customer);

        mockMvc.perform(get("/customers/{id}", saved.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("customer"))
                .andExpect(view().name("customer"));
    }

    @Test
    @DisplayName("Fetch all customers and verify count")
    void testGetAllCustomers() throws Exception {

        Customer customer1 = new Customer();
        customer1.setName("Zara Larsson");
        customer1.setTelefonNumber("0708375473");
        customer1.setEmail("zara@larsson.se");

        Customer customer2 = new Customer();
        customer2.setName("Molly Sandén");
        customer2.setTelefonNumber("0703574027");
        customer2.setEmail("molly@sanden.se");

        customerRepository.save(customer1);
        customerRepository.save(customer2);

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("customers"))
                .andExpect(view().name("customers"));

        assertThat(customerRepository.count()).isEqualTo(2);
    }
}