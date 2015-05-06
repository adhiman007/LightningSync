# LightningSync

LightningSync provides you an ease in managing your:
- Database
- REST APIs

## Using LightningSync
Copy LightningSync and GSON jar into your ``libs`` folder

## Database
#### Creating Model for Table

In this case we are creating table with name '**user**' (passed in constructor)
```sh
public class User extends LightningModel {
	@Column(name = "userId", type = "INTEGER", order = 1)
	private int userId;

	@Column(name = "userName", type = "TEXT", order = 2)
	private String userName;

	@Column(name = "password", type = "TEXT", order = 3)
	private String password;

	public User() {
		super("user");
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
```
Here ``@Column(name = "userId", type = "INTEGER", order = 1)``
<br>
determines that we are creating a column **userId** with data type **INTEGER**
<br>
**Note:** Column name and variable name must be same inorder to work properly.

The name of the table is passed in the constructor ``super("user");`` so that it can be recognised by ``LightningTable``

#### Creating Database Helper
```sh
public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "lightning.db";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		LightningHelper.createTable(db, new User());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}
```
Here the create table query is replaced with the createTable method of LightningHelper
<br>
from
```sh
String userTable = "CREATE TABLE user (userId INTEGER, userName TEXT, password TEXT)";
db.execSQL(userTable);
```
to
<br>
```sh
LightningHelper.createTable(db, new User());
```
#### Insertion
```sh
LightningTable<User> userTable = new LightningTable<User>(User.class);
User user = new User();
user.setUserId(1);
user.setUserName("admin");
user.setPassword("1234");
userTable.insert(user);
```
#### Updation
```sh
LightningTable<User> userTable = new LightningTable<User>(User.class);
User user = new User();
user.setPassword("12345678");
userTable.update(user, "userId = 1");
```
#### Deletion
```sh
LightningTable<User> userTable = new LightningTable<User>(User.class);
userTable.delete("userId = 1");
```
#### Fetching records
```sh
LightningTable<User> userTable = new LightningTable<User>(User.class);
List<User> users = userTable.getList();
List<User> users = userTable.getList("password = '1234'");
```
#### Clear Table
```sh
LightningTable<User> userTable = new LightningTable<User>(User.class);
userTable.clearTable();
```

## Rest APIs
#### Get API
To fetch the records of users
```sh
public class Response {
	private List<User> users;
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
```
We have created a Response model class that will return us the list users (base on GSON)
<br>
```sh
public class ResponseRequest extends LightningRequest<Void, Response> {

	public ResponseRequest(Activity activity) {
		super(activity);
	}

	@Override
	public String getURL() {
		return "Enter your service url here";
	}

	@Override
	public void onResponse(Response result) {
		LightningTable<User> userTable = new LightningTable<User>(User.class);
		List<User> users = result.getUsers();
		for(User user : users) {
			userTable.insert(user);
		}
	}
}
```
-
Here we are passing ``Void`` and ``Response`` as our Param and Result params. ``Param`` is used when we need to add parameters to our web service. To do this we need to ``@Override`` ``Object getParams()`` method.
<br>
- Inorder to setHeaders ``@Override`` ``public void setHeaders(HttpRequestBase request)`` method.



- Inside ``onResponse`` we will get the response prepopulate using GSON and is ready for insertion
<br>
<br>

To hit the API from your activity or fragment (if you want to handle the response inside ResponseRequest)
```sh
new ResponseRequest(getApplicationContext()).setType(ResponseRequest.GET).hit(null);
```

To hit the API from your activity or fragment (if you want to handle the response inside you same activity or class)
```sh
new ResponseRequest(getApplicationContext()).setType(ResponseRequest.GET).setCallback(new CallBack<Response>() {

	@Override
	public void onResponse(Response result, Exception e) {
		LightningTable<User> userTable = new LightningTable<User>(User.class);
        	List<User> users = response.getUsers();
        	for(User user : users) {
		        userTable.insert(user);
	        }
	}
}).hit(null);
```
#### Post API
```sh
public class UserRequest extends LightningRequest<User, Response> {

	public UserRequest(Activity activity) {
		super(activity);
	}

	@Override
	public String getURL() {
		return "Enter your service url here";
	}
	
	@Override
	public Object getParams(User param) {
		JSONObject json = new JSONObject();
		json.put("userName", param.getUserName());
		json.put("password", param.getPassword());
		return json;
	}
	
	@Override
	public void setHeaders(HttpRequestBase request) {
		request.addHeader(arg0, arg1);
	}

	@Override
	public void onResponse(Response result) {
		LightningTable<User> userTable = new LightningTable<User>(User.class);
		List<User> users = response.getUsers();
		for(User user : users) {
			userTable.insert(user);
		}
	}
}
```
- Map you params inside ``getParams``
<br>
<br>

Hitting of the API will remain same as earlier
```sh
User user = new User();
user.setUserName("admin");
user.setPassword("1234");

// Hit Service if response is handled inside 'UserRequest'
new UserRequest(getApplicationContext()).setType(ResponseRequest.POST).hit(user);
```
Here we are fetching the response in the same class where we are hitting the API
```sh
User user = new User();
user.setUserName("admin");
user.setPassword("1234");

// Hit service if response needs to be handled inside Activity or Fragment
new UserRequest(getApplicationContext()).setType(ResponseRequest.POST).setCallback(new CallBack<Response>() {

	@Override
	public void onResponse(Response result, Exception e) {
		LightningTable<User> userTable = new LightningTable<User>(User.class);
		List<User> users = response.getUsers();
		for(User user : users) {
			userTable.insert(user);
		}
	}
}).hit(user);
```
## Init LightningSync
Create a class that will ``extends Application``

```sh
public class LightningApp extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		LightningHelper.init(getApplicationContext(), new DatabaseHelper(getApplicationContext()));
	}
}
```
**Note:** However ``LightningHelper.init()`` can also be done in your launching ``Activity`` however ``Application`` is more preffered.
<br>
<br>
## Additional Libraries
- GSON 2.2.4
