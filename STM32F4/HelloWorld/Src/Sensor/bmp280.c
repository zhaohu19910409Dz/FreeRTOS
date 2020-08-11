#include "bmp280.h"
#include "i2c.h"
#include <stdio.h>

#define BMP280_CHIP_REG  	0xd0
#define BMP280_CHID_ID 	  0x58
#define BMP280_SLAVE_ADDR (0x77 << 1)


//HAL_StatusTypeDef HAL_I2C_Mem_Read(I2C_HandleTypeDef *hi2c, uint16_t DevAddress,
//uint16_t MemAddress, uint16_t MemAddSize, uint8_t *pData, uint16_t Size, uint32_t Timeout)
void bmp280_init(void)
{
	HAL_StatusTypeDef status;
	uint8_t chipid = 0;
	
	printf("bmp280_init\r\n");
	status = HAL_I2C_Mem_Read(&hi2c1, BMP280_SLAVE_ADDR,
					BMP280_CHIP_REG, I2C_MEMADD_SIZE_8BIT, &chipid, 1, 100);
	
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
