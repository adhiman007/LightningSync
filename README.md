# LightningSync

LightningSync provides you an ease in managing your:
- Database
- Services

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

## Service
#### Get Service
To fetch the records of users
```sh
// Required for GSON
public class Response {
	private List<User> users;
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}

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

// Hit Service if response is handled inside 'ResponseRequest'
new ResponseRequest().get(getApplicationContext());

// Hit service if response needs to be handled inside Activity or Fragment
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
#### Post Service
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

User user = new User();
user.setUserName("admin");
user.setPassword("1234");

// Hit Service if response is handled inside 'UserRequest'
new UserRequest().post(getApplicationContext(), user);

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

## Additional Libraries
- GSON 2.2.4
