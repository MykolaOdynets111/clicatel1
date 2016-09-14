
package com.clickatell.models.mc2.googlemodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Geometry {

    private Bounds bounds;
    private Location location;
    @JsonProperty("location_type")
    private String locationType;
    private Viewport viewport;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Geometry() {
    }

    /**
     * 
     * @param bounds
     * @param viewport
     * @param location
     * @param locationType
     */
    public Geometry(Bounds bounds, Location location, String locationType, Viewport viewport) {
        this.bounds = bounds;
        this.location = location;
        this.locationType = locationType;
        this.viewport = viewport;
    }

    /**
     * 
     * @return
     *     The bounds
     */
    public Bounds getBounds() {
        return bounds;
    }

    /**
     * 
     * @param bounds
     *     The bounds
     */
    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    public Geometry withBounds(Bounds bounds) {
        this.bounds = bounds;
        return this;
    }

    /**
     * 
     * @return
     *     The location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * 
     * @param location
     *     The location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    public Geometry withLocation(Location location) {
        this.location = location;
        return this;
    }

    /**
     * 
     * @return
     *     The locationType
     */
    public String getLocationType() {
        return locationType;
    }

    /**
     * 
     * @param locationType
     *     The location_type
     */
    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public Geometry withLocationType(String locationType) {
        this.locationType = locationType;
        return this;
    }

    /**
     * 
     * @return
     *     The viewport
     */
    public Viewport getViewport() {
        return viewport;
    }

    /**
     * 
     * @param viewport
     *     The viewport
     */
    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public Geometry withViewport(Viewport viewport) {
        this.viewport = viewport;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(bounds).append(location).append(locationType).append(viewport).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Geometry) == false) {
            return false;
        }
        Geometry rhs = ((Geometry) other);
        return new EqualsBuilder().append(bounds, rhs.bounds).append(location, rhs.location).append(locationType, rhs.locationType).append(viewport, rhs.viewport).isEquals();
    }

}
