package hello.data.values;

// 実際にはDBから情報を取ってくるので domain パッケージにエンティティを実装するのが正しい。

public class UserInfo {
	public final String userId;
	public final String password;
	public UserInfo( String userId, String password ) {
		this.userId = userId;
		this.password = password;
	}
}
