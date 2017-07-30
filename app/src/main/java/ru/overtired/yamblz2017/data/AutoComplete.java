
package ru.overtired.yamblz2017.data;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AutoComplete {

    @SerializedName("RESULTS")
    @Expose
    private List<Result> rESULTS = null;

    public List<Result> getRESULTS() {
        return rESULTS;
    }

    public void setRESULTS(List<Result> rESULTS) {
        this.rESULTS = rESULTS;
    }

}
