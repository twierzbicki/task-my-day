package com.example.tomek.taskmyday;

import java.io.Serializable;


public class Event implements Serializable{

    private static final long serialVersionUID = 100L;
    private long Id;
    private String Name;
    private int Group;
    private boolean Syncable;
    private int Type;
    private String Description;
    private String Created;
    private String DueDate;
    private int Priority;
    private String Place;

    public Event(long id, String name, int group, boolean syncable, int type, String description, String created, String dueDate, int priority, String place) {
        this.Id = id;
        this.Name = name;
        this.Group = group;
        this.Syncable = syncable;
        this.Type = type;
        this.Description = description;
        this.Created = created;
        this.DueDate = dueDate;
        this.Priority = priority;
        this.Place = place;
    }

    public Event() {

    }

    public void setId(int id)
    {
        this.Id = id;
    }
    public long getId()
    {
        return this.Id;
    }

    public void setName(String name)
    {
        this.Name = name;
    }

    public String getName()
    {
        return this.Name;
    }

    public void setGroup (int group)
    {
        this.Group = group;
    }

    public int getGroup()
    {
        return this.Group;
    }

    public void setSyncable (Boolean syncable)
    {
        this.Syncable = syncable;
    }

    public boolean getSyncable()
    {
        return this.Syncable;
    }

    public void setType (int type)
    {
        this.Type = type;
    }

    public int getType()
    {
        return this.Type;
    }

    public void setCreated(String date)
    {
        this.Created = date;
    }

    public String getCreated ()
    {
        return Created;
    }

    public void setDueDate (String date)
    {
        this.DueDate = date;
    }

    public String getDueDate()
    {
        return this.DueDate;
    }

    public void setPriority (int priority)
    {
        this.Priority = priority;
    }

    public int getPriority()
    {
        return this.Priority;
    }

    public void setDescription (String description)
    {
        this.Description = description;
    }

    public String getDescription()
    {
        return this.Description;
    }

    public void setPlace (String place)
    {
        this.Place = place;
    }

    public String getPlace()
    {
        return this.Place;
    }

    @Override
    public String toString() {
        return "Event:" +
                "\nid: " + Id +
                "\nname" + Name +
                "\ngroup: " + Group +
                "\nsyncable: " + Syncable +
                "\ntype: " + Type +
                "\ndescription: " + Description +
                "\ncreated: " + Created +
                "\ndue_date: " + DueDate +
                "\npriority: " + Priority +
                "\nplace: " + Place;
    }
}