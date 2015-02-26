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
	private static final long serialVersionUID = 2037155306102606510L;

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

#### Creating Table for corresponding Model
```sh
public class UserTable extends LightningTable<User> {

	public UserTable() {
		super(new User());
	}

	@Override
	protected SQLiteOpenHelper getDBHelper() {
		return new DatabaseHelper(getContext());
	}

	@Override
	protected User populate(Cursor cursor) {
		User user = new User();
		user.setUserId(cursor.getInt(cursor.getColumnIndex("userId")));
		user.setUserName(cursor.getString(cursor.getColumnIndex("userName")));
		user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
		return user;
	}
}
```
- Inside constructor an instance of the model class must be passed ``super(new User());``
- Inside ``getDBHelper()`` an instance of DatabaseHeler class must be passed ``DatabaseHelper extends SQLiteOpenHelper``
- Inside ``populate(Cursor cursor)`` we need to map the cursor values with our model

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
UserTable userTable = new UserTable();
User user = new User();
user.setUserId(1);
user.setUserName("admin");
user.setPassword("1234");
userTable.insert(user);
```
#### Updation
```sh
UserTable userTable = new UserTable();
User user = new User();
user.setPassword("12345678");
userTable.update(user, "userId = 1");
```
#### Deletion
```sh
UserTable userTable = new UserTable();
userTable.delete("userId = 1", null);
```
#### Fetching records
```sh
UserTable userTable = new UserTable();
List<User> users = userTable.getList();
List<User> users = userTable.getFilterList("password = '1234'");
```
#### Clear Table
```sh
UserTable userTable = new UserTable();
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
public class ResponseRequest extends LightningGetRequest<Response> {

	public ResponseRequest() {
		super(Response.class);
	}

	@Override
	protected String getServiceUrl() {
		return "Enter your service url here";
	}
	
	@Override
	protected void onResponse(Response response) {
		UserTable userTable = new UserTable();
		List<User> users = response.getUsers();
		for(User user : users) {
			userTable.insert(user);
		}
	}
}
```
- Inside constructor we are passing the class name as we are using GSON internally for parsing ``super(Response.class);`` 
- Inside ``onResponse`` we will get the response prepopulate using GSON and is ready for insertion
<br>
<br>

To hit the API from your activity or fragment (if you want to handle the response inside ResponseRequest)
```sh
new ResponseRequest().get(getApplicationContext());
```

To hit the API from your activity or fragment (if you want to handle the response inside you same activity or class)
```sh
new ResponseRequest().setCallback(new RequestCallback<Response>() {
			
	@Override
	public void onResponse(Response response, Exception e) {
		UserTable userTable = new UserTable();
        List<User> users = response.getUsers();
        for(User user : users) {
	        userTable.insert(user);
        }
	}
}).get(getApplicationContext());
```
#### Post API
```sh
public class UserRequest extends LightningJSONPostRequest<User> {

	@Override
	protected String getServiceUrl() {
		return "Enter your service url here";
	}

	@Override
	protected void onResponse(String response) {
		// Parse your response here
	}

	@Override
	protected void populateParams(User model, JSONObject params) {
		try {
			params.put("userName", model.getUserName());
			params.put("password", model.getPassword());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
```
- Map you params inside ``populateParams``
- Response will be returned in String format that can be parsed accordingly
<br>
<br>

Hitting of the API will remain same as of Get API only need to pass the model inside the ``post()``
```sh
User user = new User();
user.setUserName("admin");
user.setPassword("1234");

// Hit Service if response is handled inside 'UserRequest'
new UserRequest().post(getApplicationContext(), user);
```
Here we are fetching the response in the same class where we are hitting the API
```sh
User user = new User();
user.setUserName("admin");
user.setPassword("1234");

// Hit service if response needs to be handled inside Activity or Fragment
new UserRequest().setCallback(new PostCallback() {
	
	@Override
	public void onResponse(String response, Exception e) {
		// Parse your response here
	}
}).post(getApplicationContext(), user);
```
## Init LightningSync
Create a class that will ``extends Application``

```sh
public class LightningApp extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		LightningHelper.init(getApplicationContext());
	}
}
```
**Note:** However ``LightningHelper.init()`` can also be done in your launching ``Activity`` however ``Application`` is more preffered.
<br>
<br>
## Additional Libraries
- GSON 2.2.4
