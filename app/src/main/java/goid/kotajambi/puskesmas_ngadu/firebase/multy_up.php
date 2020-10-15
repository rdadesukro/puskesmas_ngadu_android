 <?php
 require_once('dbConnect.php');
  $newname = date('YmdHis',time()).mt_rand().'.jpg';
  $random = random_word(20);
  $file_path = "images/".$random;
$full_path="http://bishoy.esy.es/retrofit/".$file_path;
$img = $_FILES['file'];
$response['message'] = "names : ";
$uuid=addslashes(trim($_POST['uuid']));

$uuid2=addslashes(trim($_POST['uuid2']));
$layanan_id=addslashes(trim($_POST['layanan_id']));
if(!empty($img)){

   for($i=0;$i<count($_FILES['file']['tmp_name']);$i++){
     $tes = $file_path.$_FILES['file']['name'][$i];
	 $syarat = addslashes(trim($_POST['syarat'][$i]));
     $response['error'] = false;
     $response['message'] =  "number of files recieved is = ".count($_FILES['file']['name']);
     if(move_uploaded_file($_FILES['file']['tmp_name'][$i],$file_path.$_FILES['file']['name'][$i])){
           $response['error'] = false;
     $response['message'] =  $response['message']. "moved sucessfully ::  ";
	 
	 
	  $result = mysqli_query($con, "SELECT id FROM registrasis where uuid='$uuid'");
                  while($row = mysqli_fetch_assoc($result)){
                  $output=$row; 
				  $kalimat = implode(" ",$output);

}
 $result = mysqli_query($con, "SELECT layanans.`nama` FROM registrasis,layanans WHERE registrasis.uuid='$uuid' AND registrasis.`layanan_id`=layanans.`id`");
                  while($row = mysqli_fetch_assoc($result)){
                  $output=$row; 
				  $na = implode(" ",$output);

}
	$query1 = "INSERT INTO registrasi_syarats (
id,
registrasi_id,
syarat_id,
syarat_value,
status,
created_at,
created_by,
updated_at,
updated_by,
uuid) VALUES (0,'70','$syarat','$tes.','1','1','1','1','1','$uuid2')";

	          
  
		
     if(mysqli_query($con,$query1)) {
       $response["value"] = "1";
	 //  $response["nama"] = $na;
	  // $response["id_registrasi"] = $kalimat;
       $response["message"] = "Sukses mendaftar";
      // echo json_encode($response);
     } else {
       $response["value"] = "0";
       $response["message"] = "oops! Coba lagi xx!";
      // echo json_encode($response);
     }
	 

     }else{
     $response['error'] = true;
     $response['message'] = $response['message'] ."cant move :::" .$file_path ;

     }
    }
   }  
else{
     $response['error'] = true;
     $response['message'] =  "no files recieved !";
}

echo json_encode($response);

function random_word($id = 20){
		$pool = '1234567890abcdefghijkmnpqrstuvwxyz';
		
		$word = '';
		for ($i = 0; $i < $id; $i++){
			$word .= substr($pool, mt_rand(0, strlen($pool) -1), 1);
		}
		return $word; 
	}
?>
