package com.tonggou.andclient.network.parser;

import com.tonggou.andclient.jsonresponse.BaseResponse;

/**
 * �첽��������ͨ�ý���������
 * <p> �������첽�ģ�JSON������ͬ����
 * <p> �����Ƕ� {@link AsyncJsonResponseParseHandler} �����չ������� OnSuccess �����н��� JSON ����
 * @author lwz
 *
 * @param <T>
 */
public abstract class AsyncJsonBaseResponseParseHandler<T extends BaseResponse> extends AsyncJsonResponseParseHandler<T> {
	
	@Override
	public IResponseParser<T> getJsonResponseParser() {
		return new BaseResponseParser<T>(getTypeClass());
	}
	
	public abstract Class<T> getTypeClass();
	
}