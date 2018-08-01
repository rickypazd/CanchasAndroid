package complementos;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class TablaDynamic {
    private TableLayout tableLayout;
    private Context context;
    private ArrayList<infoCelda> header;
    private ArrayList<infoCelda[]>data;
    private TableRow tableRow;
    private TextView txtcell;
    private int indexC;
    private int indexR;
    private SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private ArrayList<infoCelda> ahReservar = new ArrayList<>();
    private TextView tv_horas;
    private TextView tv_total;
    public TablaDynamic(TableLayout tableLayout, Context context,TextView tv_horas,TextView tv_total) {
        this.tableLayout = tableLayout;
        this.context = context;
        this.tv_horas=tv_horas;
        this.tv_total=tv_total;
    }

    public void addHeader(ArrayList<infoCelda> header){
        this.header=header;
    }

    public void addData(ArrayList<infoCelda[]>data){
        this.data=data;
    }

    private void newRow(){
        tableRow=new TableRow(context);
    }

    private void newCell(){
        txtcell=new TextView(context);
        txtcell.setGravity(Gravity.CENTER);
        txtcell.setTextSize(18);
        txtcell.setBackgroundColor(Color.WHITE);
    }
    public void createHeader(){
        indexC=0;
        newRow();
        while (indexC<header.size()){
            newCell();
            txtcell.setTextSize(16);
            infoCelda info = header.get(indexC++);
            txtcell.setTag(info);
            txtcell.setText(info.getTexto());
            txtcell.setTextColor(Color.BLACK);
            tableRow.addView(txtcell,newTableRowParams());
        }
        tableLayout.addView(tableRow);
    }
    public void createHeaderdia(){
        indexC=0;
        newRow();
        while (indexC<header.size()){
            newCell();
            txtcell.setTextSize(16);

            infoCelda info = header.get(indexC++);
            txtcell.setTag(info);
            txtcell.setText(info.getdia());
            txtcell.setTextColor(Color.BLACK);
            tableRow.addView(txtcell,newTableRowParams());
        }
        tableLayout.addView(tableRow);
        createHeader();
    }
    public void createDataTable(){
        String info;
        for (indexR=1;indexR<=data.size();indexR++){
            newRow();
            for (indexC=0;indexC<header.size();indexC++){
                newCell();
                infoCelda[] colums = data.get(indexR-1);
                infoCelda cel =colums[indexC];
                txtcell.setTag(cel);
                if(cel.getTipo()==3){
                    txtcell.setText(cel.getTexto());
                    txtcell.setTextColor(Color.BLACK);
                }else if(cel.getTipo()==4){
                    if(cel.getAbierto()==1){
                        txtcell.setText(cel.getTexto());
                        if(cel.getEstado()==1){//pendiente
                            txtcell.setBackgroundColor(Color.rgb(243,239,58));
                        }else if(cel.getEstado()==2){ //confirmado
                            txtcell.setBackgroundColor(Color.rgb(99,170,42));
                        }else {
                            txtcell.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    infoCelda cel = (infoCelda) v.getTag();
                                    if(ahReservar.contains(cel)){
                                        ahReservar.remove(cel);
                                        v.setBackgroundColor(Color.rgb(255,255,255));
                                        calcular();
                                    }else{
                                        Calendar cal = Calendar.getInstance();
                                        cal.add(Calendar.HOUR,+3);
                                        if(cal.getTime().after(cel.getDateFecha())){
                                            Toast.makeText(context,"deve reservar con 3 horas de anticipacion",Toast.LENGTH_LONG).show();
                                        }else{
                                            ahReservar.add(cel);
                                            v.setBackgroundColor(Color.rgb(99,170,42));
                                            calcular();
                                        }

                                    }
                                }
                            });
                        }
                    }else{
                        txtcell.setText("");
                        txtcell.setBackgroundColor(Color.rgb(65,65,65));
                    }

                }
                tableRow.addView(txtcell,newTableRowParams());
            }
            tableLayout.addView(tableRow);
        }
    }
    private TableRow.LayoutParams newTableRowParams(){
        TableRow.LayoutParams params= new TableRow.LayoutParams();
        params.setMargins(1,1,1,1);
        params.weight=1;
        return params;
    }

    private void calcular(){
        int total=0;
        for (int i = 0; i <ahReservar.size() ; i++) {
            total+=ahReservar.get(i).getCosto();
        }
        tv_horas.setText(ahReservar.size()+"");
        tv_total.setText(total+" Bs");
    }

    public ArrayList<infoCelda> getAhReservar() {
        return ahReservar;
    }

    public void setAhReservar(ArrayList<infoCelda> ahReservar) {
        this.ahReservar = ahReservar;
    }
}
