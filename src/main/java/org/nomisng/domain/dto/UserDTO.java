package org.nomisng.domain.dto;

import lombok.ToString;
import org.nomisng.domain.entity.ApplicationUserCboProject;
import org.nomisng.domain.entity.Permission;
import org.nomisng.domain.entity.Role;
import org.nomisng.domain.entity.User;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDTO {
    private Long id;
    private String userName;
    private Set<String> roles;
    private Set<String> permissions;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String gender;
    private LocalDate dateOfBirth;
    private Long currentCboProjectId;
    private Long cboProjectId;
    @ToString.Exclude
    private List<ApplicationUserCboProject> applicationUserCboProjects;
    private String currentCboProjectDescription;

    public UserDTO(User user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.roles = user.getRole().stream().map(Role::getName).collect(Collectors.toSet());
        permissions = new HashSet<>();
        user.getRole().forEach(roles1 -> {
            permissions.addAll(roles1.getPermission().stream().map(Permission::getName).collect(Collectors.toSet()));
        });
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.gender = user.getGender();
        this.currentCboProjectId = user.getCurrentCboProjectId();
        this.applicationUserCboProjects = user.getApplicationUserCboProjects();
        this.currentCboProjectDescription = user.getCboProjectByCurrentCboProjectId() != null ? user.getCboProjectByCurrentCboProjectId().getDescription() : null;
    }

    @Override
    public String toString() {
        return "UserDTO{" + "id=" + id + ", userName=\'" + userName + '\'' + ", firstName=\'" + firstName + '\'' + ", lastName=\'" + lastName + '\'' + ", email=\'" + email + '\'' + ", phoneNumber=\'" + phoneNumber + '\'' + ", gender=\'" + gender + '\'' + ", currentCboProjectId=\'" + currentCboProjectId + '\'' + ", cboProjectId=\'" + cboProjectId + '\'' + ", roles=" + roles + '\'' + ", permissions=" + permissions + '\'' + '}';
    }

    //<editor-fold defaultstate="collapsed" desc="delombok">
    @SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @SuppressWarnings("all")
    public String getUserName() {
        return this.userName;
    }

    @SuppressWarnings("all")
    public Set<String> getRoles() {
        return this.roles;
    }

    @SuppressWarnings("all")
    public Set<String> getPermissions() {
        return this.permissions;
    }

    @SuppressWarnings("all")
    public String getFirstName() {
        return this.firstName;
    }

    @SuppressWarnings("all")
    public String getLastName() {
        return this.lastName;
    }

    @SuppressWarnings("all")
    public String getEmail() {
        return this.email;
    }

    @SuppressWarnings("all")
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    @SuppressWarnings("all")
    public String getGender() {
        return this.gender;
    }

    @SuppressWarnings("all")
    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    @SuppressWarnings("all")
    public Long getCurrentCboProjectId() {
        return this.currentCboProjectId;
    }

    @SuppressWarnings("all")
    public Long getCboProjectId() {
        return this.cboProjectId;
    }

    @SuppressWarnings("all")
    public List<ApplicationUserCboProject> getApplicationUserCboProjects() {
        return this.applicationUserCboProjects;
    }

    @SuppressWarnings("all")
    public String getCurrentCboProjectDescription() {
        return this.currentCboProjectDescription;
    }

    @SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    @SuppressWarnings("all")
    public void setUserName(final String userName) {
        this.userName = userName;
    }

    @SuppressWarnings("all")
    public void setRoles(final Set<String> roles) {
        this.roles = roles;
    }

    @SuppressWarnings("all")
    public void setPermissions(final Set<String> permissions) {
        this.permissions = permissions;
    }

    @SuppressWarnings("all")
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    @SuppressWarnings("all")
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    @SuppressWarnings("all")
    public void setEmail(final String email) {
        this.email = email;
    }

    @SuppressWarnings("all")
    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @SuppressWarnings("all")
    public void setGender(final String gender) {
        this.gender = gender;
    }

    @SuppressWarnings("all")
    public void setDateOfBirth(final LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @SuppressWarnings("all")
    public void setCurrentCboProjectId(final Long currentCboProjectId) {
        this.currentCboProjectId = currentCboProjectId;
    }

    @SuppressWarnings("all")
    public void setCboProjectId(final Long cboProjectId) {
        this.cboProjectId = cboProjectId;
    }

    @SuppressWarnings("all")
    public void setApplicationUserCboProjects(final List<ApplicationUserCboProject> applicationUserCboProjects) {
        this.applicationUserCboProjects = applicationUserCboProjects;
    }

    @SuppressWarnings("all")
    public void setCurrentCboProjectDescription(final String currentCboProjectDescription) {
        this.currentCboProjectDescription = currentCboProjectDescription;
    }

    @SuppressWarnings("all")
    public UserDTO() {
    }
    //</editor-fold>
}
