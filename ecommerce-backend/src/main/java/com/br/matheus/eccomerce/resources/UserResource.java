package com.br.matheus.eccomerce.resources;

import com.br.matheus.eccomerce.models.User;
import com.br.matheus.eccomerce.models.dto.UserDTO;
import com.br.matheus.eccomerce.repositories.UserRepository;
import com.br.matheus.eccomerce.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserResource {

    @Autowired
    private UserService userService;

    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    @Secured({"ROLE_USER"})
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        User newUser = userService.findById(id);
        return ResponseEntity.ok(newUser.toDto());
    }

    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<UserDTO> insert(@RequestBody UserDTO userDTO, UriComponentsBuilder uriBuilder) {
        URI uri = uriBuilder.path("/user/{id}").buildAndExpand(userDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(userService.insert( new User(userDTO) ).toDto());
    }

    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    @Secured({"ROLE_USER"})
    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable page) {
        Page<User> users = userService.findAll(page);
        return ResponseEntity.ok( users.stream().map( (user) -> user.toDto() ).toList());
    }

    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO userDTO, @PathVariable(name = "id") Long id) {
        User user = new User(userDTO);
        user.setId(id);
        return ResponseEntity.ok( userService.update(user).toDto() );
    }

    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
