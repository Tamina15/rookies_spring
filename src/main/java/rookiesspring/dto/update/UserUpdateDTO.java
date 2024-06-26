/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.dto.update;

import jakarta.validation.constraints.NotNull;

/**
 *
 * @author HP
 * @author Tamina
 */
public record UserUpdateDTO(
        @NotNull(message = "Id cannot be null")
        long id,
        String username, String email, String firstname, String lastname, boolean gender, int age, String phone, String address_number, String street, String ward, String district, String city, String province, String country) {

}
