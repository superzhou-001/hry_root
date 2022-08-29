/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-10 11:34:53 
 */
package hry.scm.project.service.impl;

import hry.bean.JsonResult;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.QueryFilter;
import hry.scm.project.dao.StorageDao;
import hry.scm.project.model.Storage;
import hry.scm.project.service.StorageService;
import hry.util.file.FileUtil;
import hry.utils.ExcelUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p> StorageService </p>
 *
 * @author: luyue
 * @Date: 2020-07-10 11:34:53 
 */
@Service("storageService")
public class StorageServiceImpl extends BaseServiceImpl<Storage, Long> implements StorageService {

	@Resource(name = "storageDao")
	@Override
	public void setDao (BaseDao<Storage, Long> dao) {
		super.dao = dao;
	}

	SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public JsonResult importExcel(MultipartFile file) throws IOException {
		JsonResult jsonResult=new JsonResult();

		//1、判断文件名是否为空
		String fileName = file.getOriginalFilename();//文件名
		if(StringUtils.isEmpty(fileName)){
			jsonResult=new JsonResult().setSuccess(false).setMsg("未知文件类型");
			return jsonResult ;
		}
		//2、判断上传文件是否是excel
		String exten = FileUtil.getExtensionName(fileName);//文件扩展名
		if(exten.equals("xls") || exten.equals("xlsx")){
			//3、删除所有未抵押数据
			((StorageDao)dao).removeStorage("0");
			//4、开始解析excel
			InputStream inputStream = file.getInputStream();
			Workbook wb = ExcelUtil.getWorkbook(inputStream,exten);
			Sheet sheet = wb.getSheetAt(0);
			List<Storage> list = new ArrayList<>();
			StringBuffer buffer = new StringBuffer("");
			int count = 0;
			for(int i = 2;i<=sheet.getLastRowNum();i++){
				try{
				Storage storage=new Storage();
				Row row = sheet.getRow(i);
				//5、先读取区列行层拼接的唯一标识以及社会信用代码，查询表中是否有数据，如有，则无需操作
				String number = ExcelUtil.getCellValue(row.getCell(10), true).toString();//区列行组成唯一标识
				String creditCode = ExcelUtil.getCellValue(row.getCell(23), true).toString();//企业统一社会信用代码
				QueryFilter filter=new QueryFilter(Storage.class);
				filter.addFilter("number=",number);
				filter.addFilter("creditCode=",creditCode);
				List<Storage> list1=this.find(filter);
				if(null!=list1 && list1.size()>0){
					continue;
				}
				SimpleDateFormat sdf1= new SimpleDateFormat("yyyy/MM/dd");
				//6、读取其他字段
				String goodsName = ExcelUtil.getCellValue(row.getCell(1), true).toString();//货物名称
				String inspectionNo = ExcelUtil.getCellValue(row.getCell(2), true).toString();//报检号
				String specs = ExcelUtil.getCellValue(row.getCell(3), true).toString();//规格包装
				String caseNo = ExcelUtil.getCellValue(row.getCell(4), true).toString();//箱号
				String location = ExcelUtil.getCellValue(row.getCell(5), true).toString();//库位
				String area = ExcelUtil.getCellValue(row.getCell(6), true).toString();//区
				String line = ExcelUtil.getCellValue(row.getCell(7), true).toString();//行
				String queue = ExcelUtil.getCellValue(row.getCell(8), true).toString();//列
				String layer = ExcelUtil.getCellValue(row.getCell(9), true).toString();//层
				String goodsCount = ExcelUtil.getCellValue(row.getCell(11), true).toString();//库存件数
				String weight = ExcelUtil.getCellValue(row.getCell(12), true).toString();//库存重量
				String volume = ExcelUtil.getCellValue(row.getCell(13), true).toString();//库存体积
				String price = ExcelUtil.getCellValue(row.getCell(14), true).toString();//单价
				String totalPrice = ExcelUtil.getCellValue(row.getCell(15), true).toString();//总价
				String tallyName = ExcelUtil.getCellValue(row.getCell(16), true).toString();//理货人员
				String putInDate = ExcelUtil.getCellValue(row.getCell(17), true).toString();//入库日期
				String entrustNo = ExcelUtil.getCellValue(row.getCell(18), true).toString();//委托单号
				String status = ExcelUtil.getCellValue(row.getCell(19), true).toString();//盘存状态
				String qualifiedDate = ExcelUtil.getCellValue(row.getCell(20), true).toString();//合格时间
				String goodsNo = ExcelUtil.getCellValue(row.getCell(21), true).toString();//货代
				String goodsOwner = ExcelUtil.getCellValue(row.getCell(22), true).toString();//货主
				String sampleCount = ExcelUtil.getCellValue(row.getCell(24), true).toString();//取样件数
				String sampleDate = ExcelUtil.getCellValue(row.getCell(25), true).toString();//取样日期
				String isDistribute = ExcelUtil.getCellValue(row.getCell(26), true).toString();//是否分配出库托盘
				String distributeName = ExcelUtil.getCellValue(row.getCell(27), true).toString();//分配出库托盘人员
				String distributeDate = ExcelUtil.getCellValue(row.getCell(28), true).toString();//分配出库托盘时间
				String breakCount = ExcelUtil.getCellValue(row.getCell(29), true).toString();//破损数
				String breakStatus = ExcelUtil.getCellValue(row.getCell(30), true).toString();//破损状态
				String isReturn = ExcelUtil.getCellValue(row.getCell(31), true).toString();//是否返样
				String returnDate = ExcelUtil.getCellValue(row.getCell(32), true).toString();//返样时间
				String returnCount = ExcelUtil.getCellValue(row.getCell(33), true).toString();//返样数量
                //7、封装实体对象
				storage.setGoodsName(goodsName.trim());
				storage.setNumber(number.trim());
				storage.setInspectionNo(inspectionNo.trim());
				storage.setCaseNo(caseNo.trim());
				storage.setLocation(location);
				storage.setArea(area);
				storage.setLine(line);
				storage.setQueue(queue);
				storage.setLayer(layer);
				storage.setGoodsCount(Integer.valueOf(goodsCount));
				storage.setWeight(new BigDecimal(weight));
				storage.setVolume(new BigDecimal(volume));
				storage.setPrice(new BigDecimal(price));
				storage.setTotalPrice(new BigDecimal(totalPrice));
				storage.setPutInDate(StringUtils.isNotEmpty(putInDate)?sdf1.parse(putInDate):null);
				storage.setEntrustNo(entrustNo);
				storage.setQualifiedDate(StringUtils.isNotEmpty(qualifiedDate)?sdf1.parse(qualifiedDate):null);
				storage.setGoodsNo(goodsNo.trim());
				storage.setGoodsOwner(goodsOwner.trim());
				storage.setCreditCode(creditCode.trim());
				storage.setSpecs(specs);
				storage.setTallyName(tallyName);
				storage.setStatus(status);
				storage.setSampleCount(StringUtils.isNotEmpty(sampleCount)?Integer.valueOf(sampleCount):null);
				storage.setSampleDate(StringUtils.isNotEmpty(sampleDate)?sdf1.parse(sampleDate):null);
				storage.setIsDistribute(isDistribute);
				storage.setDistributeName(distributeName);
				storage.setDistributeDate(StringUtils.isNotEmpty(distributeDate)?sdf1.parse(distributeDate):null);
				storage.setBreakCount(StringUtils.isNotEmpty(breakCount)?Integer.valueOf(breakCount):0);
				storage.setBreakStatus(breakStatus);
				storage.setIsReturn(isReturn);
				storage.setReturnDate(StringUtils.isNotEmpty(returnDate)?sdf1.parse(returnDate):null);
				storage.setReturnCount(StringUtils.isNotEmpty(returnCount)?Integer.valueOf(returnCount):0);
                this.save(storage);//保存实体对象
				 count++;
			}catch(Exception e){
					buffer.append(i-1).append("、");
				e.printStackTrace();
			 }
				jsonResult.setSuccess(true).setMsg("导入成功！一共导入："+(sheet.getLastRowNum()-1)+"条数据,成功"+count+"条。失败数据为第："+("".equals(buffer.toString())?"":buffer.toString().substring(0,buffer.toString().length()-1))+"条。");
			}
		}else{
			jsonResult=new JsonResult().setSuccess(false).setMsg("请选择excel文件");
			return   jsonResult;
		 }

		return   jsonResult;
	}

	@Override
	public List<Storage> findStorage(Map<String, String> map) {
		return ((StorageDao)dao).findStorage(map);
	}


}
