package centralcpccommittee.shopwithfriends.DataHandler;

import android.app.Activity;

import com.firebase.client.Firebase;

import centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates.DPState;

/**
 * Created by Yuhui on 4/7/2015.
 */
public class DataProcessor {

    private DPState state;

    public DataProcessor() {
        state = null;
    }
    public DataProcessor( DPState state) {
        this.state = state;
    }

    public boolean process() {
        return state.process();
    }

    public void setState(DPState state) {
        this.state = state;
    }

    public DPState getState() {
        return state;
    }
}
