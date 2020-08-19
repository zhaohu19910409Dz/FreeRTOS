#include "bmp280.h"
#include "i2c.h"
#include <stdio.h>

#define BMP280_CHIP_REG  	0xd0
#define BMP280_CHID_ID 	  	0x58
#define BMP280_SLAVE_ADDR 	(0x76 << 1)

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
	return;
}

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