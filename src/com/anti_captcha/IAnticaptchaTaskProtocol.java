package com.anti_captcha;

import org.json.JSONObject;
import com.anti_captcha.ApiResponse.TaskResultResponse;

public interface IAnticaptchaTaskProtocol {
	JSONObject getPostData();

	TaskResultResponse.SolutionData getTaskSolution();
}
