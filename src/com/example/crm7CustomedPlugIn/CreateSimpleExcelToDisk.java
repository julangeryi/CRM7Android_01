package com.example.crm7CustomedPlugIn;

import java.io.File;
import java.io.IOException;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class CreateSimpleExcelToDisk {

	public static void CreateExcel(String docName, List<TBPanKu> list) {

		WritableWorkbook book;
		try {
			book = Workbook.createWorkbook(new File(docName + ".xls"));
			WritableSheet sheet1 = book.createSheet("盘库信息", 0);
			Label label1 = new Label(0, 0, "卡片号");
			Label label3 = new Label(1, 0, "库存地编码（TB_STOCK中的）");
			Label label4 = new Label(2, 0, "行");
			Label label5 = new Label(3, 0, "列");
			Label label6 = new Label(4, 0, "层");
			Label label7 = new Label(5, 0, "盘库人");
			Label label8 = new Label(6, 0, "盘库时间");
			Label label9 = new Label(7, 0, "牌号");

			Label label2 = new Label(8, 0, "MES物料编码");

			sheet1.addCell(label1);
			sheet1.addCell(label2);
			sheet1.addCell(label3);
			sheet1.addCell(label4);
			sheet1.addCell(label5);
			sheet1.addCell(label6);
			sheet1.addCell(label7);
			sheet1.addCell(label8);
			sheet1.addCell(label9);

			for (int i = 0; i < list.size(); i++) {
				TBPanKu panKu = list.get(i);
				Label l1 = new Label(0, i + 1, panKu.getKph());
				Label l2 = new Label(1, i + 1, panKu.getStock_no());
				Label l3 = new Label(2, i + 1, panKu.getRow_no());
				Label l4 = new Label(3, i + 1, panKu.getColumn_no());
				Label l5 = new Label(4, i + 1, panKu.getLayer_no());
				Label l6 = new Label(5, i + 1, panKu.getPkr());
				Label l7 = new Label(6, i + 1, panKu.getPksj());
				Label l8 = new Label(7, i + 1, panKu.getPh());

				sheet1.addCell(l1);
				sheet1.addCell(l2);
				sheet1.addCell(l3);
				sheet1.addCell(l4);
				sheet1.addCell(l5);
				sheet1.addCell(l6);
				sheet1.addCell(l7);
				sheet1.addCell(l8);

			}

			book.write();
			book.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}