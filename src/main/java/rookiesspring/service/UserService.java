/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rookiesspring.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rookiesspring.dto.UserDTO;
import rookiesspring.dto.response.UserResponseDTO;
import rookiesspring.dto.response.custom.UserResponseDTOShort;
import rookiesspring.dto.update.UserUpdateDTO;
import rookiesspring.mapper.UserMapper;
import rookiesspring.mapper.UserUpdateMapper;
import rookiesspring.model.Address;
import rookiesspring.model.User;
import rookiesspring.model.UserDetail;
import rookiesspring.repository.UserRepository;
import rookiesspring.service.interfaces.UserServiceInterface;

/**
 *
 * @author HP
 * @author Tamina
 */
@Service
public class UserService implements UserServiceInterface {
    
    private UserRepository repository;
    private UserMapper mapper;
    @Autowired
    private UserUpdateMapper updateMapper;
    
    public UserService(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    
    public UserResponseDTOShort findById(Long userId) {
        return repository.findProjectedById(userId).orElseThrow(() -> new EntityNotFoundException());
    }
    
    public List<UserResponseDTOShort> findAllByUsername(String username) {
        if (username == null) {
            username = "";
        }
        return repository.findAllProjectedByUsernameContainsIgnoreCase(username);
    }
    
    public List<UserResponseDTO> findAllFull(String username) {
        if (username == null) {
            username = "";
        }
        return mapper.ToResponseDTOList(repository.findAll(username));
    }
    
    public UserResponseDTO findByIdFull(Long userId) {
        return mapper.ToResponseDTO(repository.findById(userId).orElseThrow(() -> new EntityNotFoundException()));
    }
    
    public UserResponseDTO save(UserDTO newUser) {
        User u = mapper.toEntity(newUser);
        try {
            return mapper.ToResponseDTO(repository.save(u));
        } catch (Exception e) {
            throw new EntityExistsException();
        }
    }
// doing

    public UserResponseDTOShort updateOne(UserUpdateDTO user_dto) {
        if (checkExist(user_dto.id())) {
            User u = repository.getReferenceById(user_dto.id());
            if (user_dto.email() != null) {
                if (checkExistEmail(user_dto.email())) {
                    throw new EntityExistsException("Email has already Exists");
                }
                
            }
            Address address = new Address();
            updateMapper.updateUserAddressFromDto(user_dto, address);
            UserDetail detail = new UserDetail();
            updateMapper.updateUserDetailFromDto(user_dto, detail);
            updateMapper.updateUserFromDto(user_dto, u);
            detail.setAddress(address);
            u.setUser_detail(detail);
            
            repository.save(u);
            return mapper.ToResponseDTOShort(u);
        } else {
            throw new EntityNotFoundException("No value present");
        }
    }
    
    public void deleteById(long id) {
        if (checkExist(id)) {
            repository.deleteById(id);
        } else {
            throw new EntityNotFoundException();
        }
    }
    
    @Override
    public boolean checkExist(long id) {
        return repository.existsById(id);
    }
    
    public boolean checkExistEmail(String email) {
        return repository.existsByEmail(email);
    }
}
