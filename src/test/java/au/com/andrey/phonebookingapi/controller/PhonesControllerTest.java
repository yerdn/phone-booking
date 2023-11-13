package au.com.andrey.phonebookingapi.controller;

import au.com.andrey.phonebookingapi.exception.PhoneNotAvailableException;
import au.com.andrey.phonebookingapi.exception.PhoneNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PhonesControllerTest extends BaseControllerTest {

    public PhonesControllerTest() {
        super("/phones");
    }

    @Test
    @Order(1)
    void findAllPhones() throws Exception {
        mockMvc.perform(get(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                          	"data": [
                          		{
                          			"id": "e34e5fb6-8150-11ee-b962-0242ac120002",
                          			"name": "Samsung Galaxy S9",
                          			"available": true,
                          			"booking": null
                          		},
                          		{
                          			"id": "03d524d6-8151-11ee-b962-0242ac120002",
                          			"name": "Samsung Galaxy S8",
                          			"available": true,
                          			"booking": null
                          		},
                          		{
                          			"id": "071d7db4-8151-11ee-b962-0242ac120002",
                          			"name": "Samsung Galaxy S8",
                          			"available": true,
                          			"booking": null
                          		},
                          		{
                          			"id": "6a858eb8-3031-4c61-8f75-0bed3475258e",
                          			"name": "Motorola Nexus 6",
                          			"available": true,
                          			"booking": null
                          		},
                          		{
                          			"id": "1879e5d7-e144-4e82-af0f-6bbd09be1246",
                          			"name": "Oneplus 9",
                          			"available": true,
                          			"booking": null
                          		},
                          		{
                          			"id": "5e570aa7-e297-4ec0-8bb6-72796de1a28e",
                          			"name": "Apple iPhone 13",
                          			"available": true,
                          			"booking": null
                          		},
                          		{
                          			"id": "09687069-2abb-4ed8-b370-bbdf94c22b2f",
                          			"name": "Apple iPhone 12",
                          			"available": true,
                          			"booking": null
                          		},
                          		{
                          			"id": "2cfa5042-c966-4868-a84c-5b60d300e2be",
                          			"name": "Apple iPhone 11",
                          			"available": false,
                          			"booking": {
                          				"bookingTime": "2023-11-13T22:23:19.917613+11:00",
                          				"bookedBy": "Samantha"
                          			}
                          		},
                          		{
                          			"id": "b2bd0b17-80f2-446a-8416-980a74ec69a4",
                          			"name": "iPhone X",
                          			"available": true,
                          			"booking": null
                          		},
                          		{
                          			"id": "64207b5e-5a43-47a0-88e8-e77591080f4b",
                          			"name": "Nokia 3310",
                          			"available": true,
                          			"booking": null
                          		}
                          	],
                          	"error": null,
                          	"page": {
                          		"pageSize": 20,
                          		"pageNumber": 0,
                          		"totalElements": 10,
                          		"totalPages": 1
                          	}
                          }
                        """));
    }

    @Test
    @Order(2)
    void bookPhone() throws Exception {
        mockMvc.perform(post(baseUrl + "/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                   "phones": [
                                     "e34e5fb6-8150-11ee-b962-0242ac120002",
                                     "6a858eb8-3031-4c61-8f75-0bed3475258e"
                                   ],
                                   "bookedBy": "David Jones"
                                 }
                                """))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                             "data": [
                                 {
                                     "id": "6a858eb8-3031-4c61-8f75-0bed3475258e",
                                     "name": "Motorola Nexus 6",
                                     "available": false,
                                     "booking": {
                                         "bookedBy": "David Jones"
                                     }
                                 },
                                 {
                                     "id": "e34e5fb6-8150-11ee-b962-0242ac120002",
                                     "name": "Samsung Galaxy S9",
                                     "available": false,
                                     "booking": {
                                         "bookedBy": "David Jones"
                                     }
                                 }
                             ],
                             "error": null,
                             "page": {
                                 "pageSize": 2,
                                 "pageNumber": 0,
                                 "totalElements": 2,
                                 "totalPages": 1
                             }
                         }
                        """, false));
    }

    @Test
    @Order(3)
    void shouldNotAllowToBookPhoneBooked() throws Exception {
        mockMvc.perform(post(baseUrl + "/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                   "phones": [
                                     "2cfa5042-c966-4868-a84c-5b60d300e2be"
                                   ],
                                   "bookedBy": "Samantha"
                                 }
                                """))
                .andExpect(status().isUnauthorized())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof PhoneNotAvailableException));
    }

    @Test
    @Order(4)
    void returnPhone() throws Exception {
        mockMvc.perform(post(baseUrl + "/return")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                   "phones": [
                                     "2cfa5042-c966-4868-a84c-5b60d300e2be"
                                   ],
                                   "bookedBy": "Samantha"
                                 }
                                """))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                          	"data": [
                          		{
                          			"id": "2cfa5042-c966-4868-a84c-5b60d300e2be",
                          			"name": "Apple iPhone 11",
                          			"available": true,
                          			"booking": null
                          		}
                          	],
                          	"error": null,
                          	"page": {
                          		"pageSize": 1,
                          		"pageNumber": 0,
                          		"totalElements": 1,
                          		"totalPages": 1
                          	}
                          }
                        """, false));
    }

    @Test
    @Order(5)
    void shouldNotAllowToBookPhoneUnknown() throws Exception {
        mockMvc.perform(post(baseUrl + "/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                   "phones": [
                                     "1e1536a1-2a8f-46fc-9d60-55e26fbf00e9"
                                   ],
                                   "bookedBy": "Samantha"
                                 }
                                """))
                .andExpect(status().isNotFound())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof PhoneNotFoundException));
    }
}