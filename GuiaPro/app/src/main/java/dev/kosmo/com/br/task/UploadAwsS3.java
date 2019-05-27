package dev.kosmo.com.br.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

import java.io.File;
import java.util.HashMap;

import dev.kosmo.com.br.interfaces.ImagemInterface;


public class UploadAwsS3 extends AsyncTask<String, String, HashMap<String, Object>> {

    private ProgressDialog progressDialog;
    private File arquivo;
    private String nome;
    private ImagemInterface imagemInterface;
    private Context contexto;


    public UploadAwsS3(Context contexto, File arquivo, String nome, ImagemInterface imagemInterface) {
        this.contexto = contexto;
        this.arquivo = arquivo;
        this.nome = nome;
        this.imagemInterface = imagemInterface;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(contexto);
        progressDialog.setMessage("Aguarde...");
        progressDialog.show();
    }

    @Override
    protected HashMap<String, Object> doInBackground(String... strings) {
        AmazonS3Client s3Client =   new AmazonS3Client( new BasicAWSCredentials( "AKIAIFMGWD4LMBO73FGQ",
                "tV/FNQUDngBNV1BrSxeZKa/mgOz9lMLFR/AFJ/Ax" ) );

        PutObjectRequest por =    new PutObjectRequest( "cassems-imagem" ,nome, arquivo);
        PutObjectResult putObjectResult = s3Client.putObject(por);

        putObjectResult.getETag();
        return null;
    }

    @Override
    protected void onPostExecute(HashMap<String, Object> stringObjectHashMap) {
        super.onPostExecute(stringObjectHashMap);
        progressDialog.dismiss();
        imagemInterface.retornoPostImagem(true, "https://s3-sa-east-1.amazonaws.com/cassems-imagem/" + nome + ".jpg");
    }

    /* public void uploadFileAWSS3(Context context, File arquivo, final String nome, final ImagemInterface imagemInterface){

        BasicAWSCredentials credentials = new BasicAWSCredentials("AKIAIFMGWD4LMBO73FGQ",
                "tV/FNQUDngBNV1BrSxeZKa/mgOz9lMLFR/AFJ/Ax");
        AmazonS3Client s3Client = new AmazonS3Client(credentials);

        TransferUtility transferUtility =
                TransferUtility.builder()
                        .context(context)
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .s3Client(s3Client)
                        .build();

        // "jsaS3" will be the folder that contains the file
        TransferObserver uploadObserver =
                transferUtility.upload("cassems-imagem/" + nome, arquivo);

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Aguarde...");
        progressDialog.show();


        uploadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                progressDialog.dismiss();
                if (TransferState.COMPLETED == state) {
                    imagemInterface.retornoPostImagem(true, "https://s3-sa-east-1.amazonaws.com/cassems-imagem/" + nome + ".jpg");
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                float percentDonef = ((float)bytesCurrent/(float)bytesTotal) * 100;
                int percentDone = (int)percentDonef;
                progressDialog.setMessage("Aguarde...  " + percentDone + "%");
            }

            @Override
            public void onError(int id, Exception ex) {
                progressDialog.dismiss();
            }

        });

            // If your upload does not trigger the onStateChanged method inside your
            // TransferListener, you can directly check the transfer state as shown here.
        if (TransferState.COMPLETED == uploadObserver.getState()) {
            // Handle a completed upload.
        }
    }*/
}
