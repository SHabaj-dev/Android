package com.asi.allchat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

public class chatFragment extends Fragment {

    private FirebaseFirestore firebaseFirestore;
    LinearLayoutManager linearLayoutManager;

    private FirebaseAuth firebaseAuth;
    ImageView imageViewOfUser;
    RecyclerView recyclerView;

    FirestoreRecyclerAdapter<FirebaseModel, NoteViewHolder> chatAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.chat_fragment, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        recyclerView = view.findViewById(R.id.recyclerView);

//        Query query = firebaseFirestore.collection("Users");
        Query query=firebaseFirestore.collection("Users").whereNotEqualTo("uid",firebaseAuth.getUid());
        FirestoreRecyclerOptions<FirebaseModel> allUserName = new FirestoreRecyclerOptions.Builder<FirebaseModel>()
                .setQuery(query, FirebaseModel.class).build();

        chatAdapter = new FirestoreRecyclerAdapter<FirebaseModel, NoteViewHolder>(allUserName) {
            @Override
            protected void onBindViewHolder(@NonNull NoteViewHolder noteViewHolder, int i, @NonNull FirebaseModel firebaseModel) {

                noteViewHolder.particularUserName.setText(firebaseModel.getName());

                String uri = firebaseModel.getImage();

                Picasso.get().load(uri).into(imageViewOfUser);

                if(firebaseModel.getStatus().equals("Online")){
                    noteViewHolder.userStatus.setText(firebaseModel.getStatus());
                    noteViewHolder.userStatus.setTextColor(Color.GREEN);
                }else{
                    noteViewHolder.userStatus.setText(firebaseModel.getStatus());
                }

                noteViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Toast.makeText(getActivity(), "New Chat is Clicked", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), ChatView.class);
                        intent.putExtra("name",firebaseModel.getName());
                        intent.putExtra("receiveruid",firebaseModel.getUid());
                        intent.putExtra("imageuri",firebaseModel.getImage());
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_chat_view_layout, parent, false);
                return new NoteViewHolder(v);
            }
        };

        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(chatAdapter);

        return view;
    }


    public class NoteViewHolder extends RecyclerView.ViewHolder{

        private TextView particularUserName;
        private TextView userStatus;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            particularUserName = itemView.findViewById(R.id.userNameChat);
            userStatus = itemView.findViewById(R.id.onlineStatus);
            imageViewOfUser = itemView.findViewById(R.id.userImageViewChat);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        chatAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(chatAdapter != null){
            chatAdapter.stopListening();
        }
    }
}
