package com.example.studybee.initsdk;

public interface AuthConstants {
//	ZoomSDKInitParams initParams = new ZoomSDKInitParams();
	// TODO Change it to your web domain
	public final static String WEB_DOMAIN = "zoom.us";
	// TODO Change it to your APP Key
	public final static String APP_KEY = "3DLI5TUEe4K6gXwG17lIULzOwjKv469lSp6J";
	// TODO Change it to your APP Secret
	public final static String APP_SECRET = "xx4x1arYuV0V6Ag4NaySGY1R9nLkGzQOy5Th";

	public final static String ip = "192.168.1.126";
	/**
	 * We recommend that, you can generate jwttoken on your own server instead of hardcore in the code.
	 * We hardcore it here, just to run the demo.
	 *
	 * You can generate a jwttoken on the https://jwt.io/
	 * with this payload:
	 * {
	 *     "appKey": "string", // app key
	 *     "iat": long, // access token issue timestamp
	 *     "exp": long, // access token expire time
	 *     "tokenExp": long // token expire time
	 * }
	 */
	//	public final static String SDK_JWTTOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBLZXkiOiIzRExJNVRVRWU0SzZnWHdHMTdsSVVMek93akt2NDY5bFNwNkoiLCJpYXQiOjE2MDA3MzI4MDAsImV4cCI6MTYwMDkwNTYwMCwidG9rZW5FeHAiOjE3MjgwMH0.GVJBIq_X9WEEMfiXE6sC7H6w1sTEt3RwQM3_-8mAdI8";

}

//eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..04oUH1UQZjMOcF7QuBHEbThO_8g0WNzdjJHM-gdUpkI
