package dao;

import model.Tracker;

import java.util.List;

/**
 * Created by pierremarsot on 13/10/2016.
 */
public interface ITrackerDao
{
    Tracker findById(int idTracker);
    List<Tracker> getAll();
}
