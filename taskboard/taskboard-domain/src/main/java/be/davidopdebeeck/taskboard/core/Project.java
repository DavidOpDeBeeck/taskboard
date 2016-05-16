package be.davidopdebeeck.taskboard.core;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

/**
 * Project class
 */
@Entity
@Table( name = "project" )
public class Project extends Identifiable {

    @NotBlank( message = "Title should not be empty!")
    @Column( name = "title", nullable = false )
    private String title;

    @Column( name = "password" )
    private String password;

    @Column( name = "salt" )
    private String salt;

    @OneToMany( fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true )
    @JoinTable(
            name = "project_has_lane",
            joinColumns = {@JoinColumn( name = "project_id", referencedColumnName = "id" )},
            inverseJoinColumns = {@JoinColumn( name = "lane_id", referencedColumnName = "id" )}
    )
    private Set<Lane> lanes;

    /**
     * Empty constructor
     */
    public Project() {
    }

    /**
     * Project constructor that takes a title
     *
     * @param title The title of the project
     */
    public Project( String title ) {
        this.title = title;
    }

    /**
     * Project constructor that takes a title and a plaintext password
     *
     * @param title             The title of the project
     * @param plainTextPassword The plaintext password of the project
     */
    public Project( String title, String plainTextPassword ) {
        this(title);
        this.salt = new BigInteger(130, new SecureRandom()).toString(20);
        this.password = createHash(plainTextPassword, salt);
    }

    /**
     * Adds a lane to a project
     *
     * @param lane The lane that will be added to the project
     * @return If the lane is successfully added to the project
     */
    public boolean addLane( Lane lane ) {
        if (lanes == null)
            lanes = new HashSet<>();
        return lanes.add(lane);
    }

    /**
     * Removed a lane from the project
     *
     * @param lane The lane that will be removed from the project
     * @return If the lane is successfully removed
     */
    public boolean removeLane( Lane lane ){
        if (lanes == null)
            return false;
        return lanes.remove(lane);
    }

    /**
     * Check if the given password is valid
     *
     * @param plainTextPassword The plaintext password
     * @return True if the plaintext password matches the saved password
     */
    public boolean isPasswordValid( String plainTextPassword ) {
        return createHash(plainTextPassword, salt).equals(password);
    }

    /**
     * @return If the project is secured with a password
     */
    public boolean isSecured() {
        return password != null;
    }

    /**
     * Sets the title of the project
     *
     * @param title The title of the project
     */
    public void setTitle( String title ) {
        this.title = title;
    }

    /**
     * Sets the password of the project in plaintext
     *
     * @param plainTextPassword The plaintext password of the project
     */
    public void setPassword( String plainTextPassword ) {
        this.salt = new BigInteger(130, new SecureRandom()).toString(20);
        this.password = createHash(plainTextPassword, salt);
    }

    /**
     * Sets the lanes of the project
     *
     * @param lanes The lanes of the project
     */
    public void setLanes( Set<Lane> lanes ) {
        this.lanes = lanes;
    }

    /**
     * @return The title of the project
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return The password of the project
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return The salt of the project
     */
    public String getSalt() {
        return salt;
    }

    /**
     * @return The lanes of the project
     */
    public Set<Lane> getLanes() {
        return lanes;
    }

    @Override
    public boolean equals( Object o ) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Project project = (Project) o;

        return id.equals(project.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    /**
     * Hashes the plaintext password using SHA-512
     *
     * @param plainTextPassword The password that will be hashed
     * @return The password in hashed form.
     */
    private String createHash( String plainTextPassword, String salt ) {
        if (plainTextPassword == null)
            return null;

        MessageDigest digest = null;

        try {
            digest = MessageDigest.getInstance("SHA-512");
            digest.reset();
        } catch (NoSuchAlgorithmException ignored) {
        }

        digest.update(plainTextPassword.getBytes());

        if (salt != null)
            digest.update(salt.getBytes());

        return (new BigInteger(1, digest.digest()).toString(40));
    }
}
