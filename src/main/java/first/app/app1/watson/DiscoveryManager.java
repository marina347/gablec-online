package first.app.app1.watson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibm.watson.developer_cloud.discovery.v1.Discovery;
import com.ibm.watson.developer_cloud.discovery.v1.model.AddDocumentOptions;
import com.ibm.watson.developer_cloud.discovery.v1.model.DocumentAccepted;
import com.ibm.watson.developer_cloud.discovery.v1.model.QueryOptions;
import com.ibm.watson.developer_cloud.discovery.v1.model.QueryResponse;
import com.ibm.watson.developer_cloud.http.HttpMediaType;
import com.ibm.watson.developer_cloud.http.ServiceCall;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

public class DiscoveryManager {

    private String endPoint = "https://gateway.watsonplatform.net/discovery/api";
    private String versionDate ="2017-11-07";

    private String environmentId;
    private String collectionId;
    private String username;
    private String password;
    private Discovery discovery;


    public DiscoveryManager( String environmentId ,
            String collectionId ,
            String username,
            String password) {
        this.environmentId = environmentId;
        this.collectionId = collectionId;
        this.username = username;
        this.password = password;
    }

    public void initManager()
    {
        discovery = new Discovery(versionDate);
        discovery.setUsernameAndPassword(this.username,this.password);
        discovery.setEndPoint(endPoint);
    }

    public QueryResponse fetchDataForUser(int userId)
    {

        QueryOptions queryOptions = new QueryOptions.Builder(environmentId,collectionId)
                .filter("userId::\""+(float)userId+"\", dayInWeek::\""+(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)==1?
                        7:
                        Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-1)+"\"")
                .aggregation("histogram(foodId,interval:1).sum(count)").build();
        ServiceCall<QueryResponse> queryResponseServiceCall = discovery.query(queryOptions);
        QueryResponse queryResponse = queryResponseServiceCall.execute();
        return queryResponse;
    }


    public void addDocumentToWatson(UserOrderDiscoveryModel userOrder)
    {
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(userOrder, UserOrderDiscoveryModel.class);
        AddDocumentOptions.Builder documentOptionsBuilder = new AddDocumentOptions.Builder();
        AddDocumentOptions documentOptions = documentOptionsBuilder
                .collectionId(collectionId)
                .environmentId(environmentId)
                .file(new ByteArrayInputStream(json.getBytes()))
                .filename(Calendar.getInstance().getTime().toString()+"_"+userOrder.getUserId())
                .fileContentType(HttpMediaType.APPLICATION_JSON).build();
        DocumentAccepted documentAccepted = discovery.addDocument(documentOptions).execute();

    }





}
