<?php  

//by junminwoo

$wether = $_GET['wether']; 
$tim = $_GET['tim']; 

if(!$wether){
//������ �׳� ������ ���� �ִ°��� ��������, ���Ŀ��� Ư�� �˻縦 ��ģ �� �����ϵ���.
 echo "failure";
}else{
 $data = new Setting($wether, $tim);
 $data->setShot();
}

class Setting{
 private $wether;
 private $tim;

 function __construct($wether, $tim){
   $this->wether=$wether;
   $this->tim=$tim;
   $this->con=mysqli_connect("localhost","root","autoset","test");
   mysqli_set_charset($this->con,"utf8");
 }
 public function setShot(){
   mysqli_query($this->con,"set SQL_SAFE_UPDATES = 0;");  
   $result = mysqli_query($this->con,"update we_bbs set shott = 2 where bbsno in(select bbsno from ( select * from we_bbs where wether = $this->wether and shott = 0 and tt <= '$this->tim') as tmp)");

   echo ($result ? "success!" : "failure!");

   $result = mysqli_query($this->con,"update we_bbs set shott = 1 where bbsno in(select bbsno from ( select * from we_bbs where wether != $this->wether and shott = 0 and tt <= '$this->tim') as tmp)");
  
   echo ($result ? "success!" : "failure!");

   mysqli_close($this->con);  
 }
}



?> 