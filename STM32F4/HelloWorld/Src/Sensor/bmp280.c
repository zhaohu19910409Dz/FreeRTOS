#include "bmp280.h"
#include "i2c.h"
#include <stdio.h>

#define BMP280_CHIP_REG  	0xd0
#define BMP280_CHID_ID 	  	0x58
#define BMP280_SLAVE_ADDR 	(0x76 << 1)

static int mTempCalib[3];
static int bmp280_read_bytes(uint8_t addr, uint8_t *data, uint8_t size)
{
	HAL_StatusTypeDef status;
	
	status = HAL_I2C_Mem_Read(&hi2c1, BMP280_SLAVE_ADDR, 
			addr, I2C_MEMADD_SIZE_8BIT, data, size, 100);
	
	if(status != HAL_OK)
	{
		printf("%s failed to read register,error:%d\r\n", __func__, status);
		return -1;
	}
	return 0;
}

static int bmp280_read_word(uint8_t addr)
{
	HAL_StatusTypeDef status;
	uint8_t byte_data[2];
	
	status = HAL_I2C_Mem_Read(&hi2c1, BMP280_SLAVE_ADDR, 
			addr, I2C_MEMADD_SIZE_8BIT, byte_data, 2, 100);
	
	if(status != HAL_OK)
	{
		printf("%s failed to read register,error:%d\r\n", __func__, status);
		return -1;
	}
	return (byte_data[1] << 8 | byte_data[0]);
}

static int bmp280_read_byte(uint8_t addr)
{
	HAL_StatusTypeDef status;
	uint8_t byte_data;
	
	status = HAL_I2C_Mem_Read(&hi2c1, BMP280_SLAVE_ADDR, 
			addr, I2C_MEMADD_SIZE_8BIT, &byte_data, 1, 100);
	
	if(status != HAL_OK)
	{
		printf("%s failed to read register,error:%d\r\n", __func__, status);
		return -1;
	}
	return byte_data;
}

static int bmp280_write_byte(uint8_t addr, uint8_t data)
{
	HAL_StatusTypeDef status;
	
	status = HAL_I2C_Mem_Write(&hi2c1, BMP280_SLAVE_ADDR, 
			addr, I2C_MEMADD_SIZE_8BIT, &data, 1, 100);
	
	if(status != HAL_OK)
	{
		printf("%s failed to write register,error:%d\r\n", __func__, status);
		return -1;
	}
	return 0;
}

void bmp280_init(void)
{
	HAL_StatusTypeDef status;
	uint8_t chipid = 0;
	
	printf("bmp280_init\r\n");
	status = HAL_I2C_Mem_Read(&hi2c1, BMP280_SLAVE_ADDR, BMP280_CHIP_REG, I2C_MEMADD_SIZE_8BIT, &chipid, 1, 100);
	
	if(status != HAL_OK)
	{
		printf("%s failed to read chip id,error:%d\r\n", __func__, status);
		return;
	}
	printf("%s:chip id = 0x%02x\r\n", __func__, chipid);
	
	if(chipid == BMP280_CHID_ID)
	{
		printf("%s: chip id match!!!", __func__);
	}
	
	mTempCalib[0] = bmp280_read_word(0x88) & 0xffff;
	mTempCalib[1] = bmp280_read_word(0x8A) & 0xffff;
	mTempCalib[2] = bmp280_read_word(0x8C) & 0xffff;
	bmp280_write_byte(0xf4, 0x23);//00100011
}

static float bmp280_cal_temp(int raw, int *calib)
{
	int dig_T1 = calib[0];
	int dig_T2 = calib[1];
	int dig_T3 = calib[2];
	
	float adc_T = (float)raw;
	float var1 = (((float)adc_T)/16384.0 - ((float)dig_T1)/1024.0) * ((float)dig_T2);
	float var2 = ((((float)adc_T)/131072.0 - ((float)dig_T1)/8192.0) * (((float)adc_T)/131072.0 - ((float) dig_T1)/8192.0)) * ((float)dig_T3);
	float fineTemp = var1 + var2;
	return fineTemp / 5120.0f;
}

float bmp280_get_temp(void)
{
	uint8_t buffer[3];
	bmp280_read_bytes(0xFA, buffer, 3);
	int msb = buffer[0] & 0xff;
	int lsb = buffer[1] & 0xff;
	int xlsb = buffer[2] & 0xff;
	
	int raw = (msb << 16 | lsb << 8 | xlsb) >> 4;
	
	return bmp280_cal_temp(raw, mTempCalib);
}

#if 0
static uint8_t bmp280_read_register(I2C_HandleTypeDef Bmp280_I2cHandle, uint8_t reg)
{
	uint8_t reg_data;
	
	while(HAL_I2C_Master_Transmit(&Bmp280_I2cHandle, BMP280_SLAVE_ADDR, &reg_data, 1, 1000) != HAL_OK)
	{
		if(HAL_I2C_GetError(&Bmp280_I2cHandle) != HAL_I2C_ERROR_AF)
		{
			printf("Transmit slave address error!!!\r\n");
			return -1;
		}
	}
	
	while(HAL_I2C_Master_Receive(&Bmp280_I2cHandle, BMP280_SLAVE_ADDR, &reg_data, 1, 1000) != HAL_OK)
	{
		if(HAL_I2C_GetError(&Bmp280_I2cHandle) != HAL_I2C_ERROR_AF)
		{
			printf("Receive slave address error!!!\r\n");
			return -1;
		}
	}
	return reg_data;
}

static void bmp280_write_register(I2C_HandleTypeDef Bmp280_I2cHandle, uint8_t reg_addr, uint8_t reg_data)
{
    uint8_t tx_data[2] = {reg_addr, reg_data};
 
    while(HAL_I2C_Master_Transmit(&Bmp280_I2cHandle, BMP280_SLAVE_ADDR, tx_data, 2, 10000) != HAL_OK)
	{
        if(HAL_I2C_GetError(&Bmp280_I2cHandle) != HAL_I2C_ERROR_AF)
		{
            printf("Transmit slave address error!!!\r\n");
        }
    }
}
#endif