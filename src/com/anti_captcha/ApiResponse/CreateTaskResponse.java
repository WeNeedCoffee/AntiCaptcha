package com.anti_captcha.ApiResponse;

import org.json.JSONObject;
import com.anti_captcha.Helper.DebugHelper;
import com.anti_captcha.Helper.JsonHelper;

public class CreateTaskResponse {
	private Integer errorId;
	private String errorCode;
	private String errorDescription;
	private Integer taskId;

	public CreateTaskResponse(JSONObject json) {
		errorId = JsonHelper.extractInt(json, "errorId");

		if (errorId != null) {
			if (errorId.equals(0)) {
				taskId = JsonHelper.extractInt(json, "taskId");
			} else {
				errorCode = JsonHelper.extractStr(json, "errorCode");
				errorDescription = JsonHelper.extractStr(json, "errorDescription");
			}
		} else {
			DebugHelper.out("Unknown error", DebugHelper.Type.ERROR);
		}
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorDescription() {
		return errorDescription == null ? "(no error description)" : errorDescription;
	}

	public Integer getErrorId() {
		return errorId;
	}

	public Integer getTaskId() {
		return taskId;
	}
}
